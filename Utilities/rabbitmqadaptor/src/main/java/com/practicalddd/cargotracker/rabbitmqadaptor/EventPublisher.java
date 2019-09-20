package com.practicalddd.cargotracker.rabbitmqadaptor;

import com.practicalddd.cargotracker.rabbitmqadaptor.publisher.DeliveryOptions;
import com.practicalddd.cargotracker.rabbitmqadaptor.publisher.GenericPublisher;
import com.practicalddd.cargotracker.rabbitmqadaptor.publisher.MessagePublisher;
import com.practicalddd.cargotracker.rabbitmqadaptor.publisher.PublisherReliability;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Publishes events to exchanges of a broker.
 *
 * @author christian.bick
 */
@Singleton
public class EventPublisher {

    private static Logger LOGGER = LoggerFactory.getLogger(EventPublisher.class);

    ConnectionFactory connectionFactory;

    Map<Class<?>, PublisherConfiguration> publisherConfigurations =
            new HashMap<Class<?>, PublisherConfiguration>();

    ThreadLocal<Map<Class<?>, MessagePublisher>> publishers = new ThreadLocal<Map<Class<?>, MessagePublisher>>();

    @Inject
    public EventPublisher(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Adds events of the given type to the CDI events to which the event publisher listens in order to
     * publish them. The publisher configuration is used to decide where to and how to publish messages.
     *
     * @param eventType The event type
     * @param configuration The configuration used when publishing and event
     * @param <T> The event type
     */
    public <T> void addEvent(Class<T> eventType, PublisherConfiguration configuration) {
        publisherConfigurations.put(eventType, configuration);
    }

    /**
     * Observes CDI events for remote events and publishes those events if their event type
     * was added before.
     *
     * @param event The event to publish
     * @throws IOException if the event failed to be published
     */
    public void publishEvent(@Observes Object event) throws IOException, TimeoutException {
        Class<?> eventType = event.getClass();
        LOGGER.debug("Receiving event of type {}", eventType.getSimpleName());
        if (! publisherConfigurations.containsKey(eventType)) {
            LOGGER.debug("No publisher configured for event of type {}", eventType.getSimpleName());
            return;
        }
        PublisherConfiguration publisherConfiguration = publisherConfigurations.get(eventType);
        MessagePublisher publisher = providePublisher(publisherConfiguration.reliability, eventType);
        Message message = buildMessage(publisherConfiguration, event);
        try {
            LOGGER.info("Publishing event of type {}", eventType.getSimpleName());
            publisher.publish(message, publisherConfiguration.deliveryOptions);
            LOGGER.info("Successfully published event of type {}", eventType.getSimpleName());
        } catch (IOException e) {
            LOGGER.error("Failed to publish event {}", eventType.getSimpleName(), e);
            throw e;
        } catch(TimeoutException te){
            LOGGER.error("Failed to publish event {}", eventType.getSimpleName(), te);
            throw te;
        }
    }

    /**
     * Provides a publisher with the specified reliability. Within the same thread, the same
     * producer instance is provided for the given event type.
     *
     * @param reliability The desired publisher reliability
     * @param eventType The event type
     * @return The provided publisher
     */
    MessagePublisher providePublisher(PublisherReliability reliability, Class<?> eventType) {
        Map<Class<?>, MessagePublisher> localPublishers = publishers.get();
        if (localPublishers == null) {
            localPublishers = new HashMap<Class<?>, MessagePublisher>();
            publishers.set(localPublishers);
        }
        MessagePublisher publisher = localPublishers.get(eventType);
        if (publisher == null) {
            publisher = new GenericPublisher(connectionFactory, reliability);
            localPublishers.put(eventType, publisher);
        }
        return publisher;
    }

    /**
     * A publisher configuration stores all important settings and options used for publishing and event.
     *
     * @author christian.bick
     */
    public static class PublisherConfiguration {
        public PublisherConfiguration(String exchange, String routingKey, Boolean persistent,
                                      PublisherReliability reliability, DeliveryOptions deliveryOptions,
                                      AMQP.BasicProperties basicProperties) {
            this.exchange = exchange;
            this.routingKey = routingKey;
            this.persistent = persistent;
            this.reliability = reliability;
            this.deliveryOptions = deliveryOptions;
            this.basicProperties = basicProperties;
        }

        String exchange;
        String routingKey;
        Boolean persistent;
        PublisherReliability reliability;
        DeliveryOptions deliveryOptions;
        AMQP.BasicProperties basicProperties;
    }

    /**
     * Builds a message based on a CDI event and its publisher configuration.
     *
     * @param publisherConfiguration The publisher configuration
     * @param event The CDI event
     * @return The message
     */
    static Message buildMessage(PublisherConfiguration publisherConfiguration, Object event) {
        Message message = new Message(publisherConfiguration.basicProperties)
                .exchange(publisherConfiguration.exchange)
                .routingKey(publisherConfiguration.routingKey);
        if (publisherConfiguration.persistent) {
            message.persistent();
        }
        if (event instanceof ContainsData) {
            message.body(((ContainsData) event).getData());
        } else if (event instanceof ContainsContent) {
            message.body(((ContainsContent) event).getContent());
        } else if (event instanceof ContainsId) {
            message.body(((ContainsId) event).getId());
        }
        return message;
    }
}
