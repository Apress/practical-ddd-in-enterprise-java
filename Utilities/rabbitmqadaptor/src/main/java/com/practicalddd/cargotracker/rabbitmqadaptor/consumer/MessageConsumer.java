package com.practicalddd.cargotracker.rabbitmqadaptor.consumer;

import com.practicalddd.cargotracker.rabbitmqadaptor.Message;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A simple implementation of a message consumer which implements
 * rarely used handles by logging them and providing a
 * convenient method to handle delivered messages.
 * 
 * @author christian.bick
 * @author uwe.janner
 * @author soner.dastan
 *
 */
public abstract class MessageConsumer extends ConsumerContainer.ManagedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);
    
    /**
     * {@inheritDoc}
     */
    public void handleConsumeOk(String consumerTag) {
        LOGGER.debug("Consumer {}: Received consume OK", consumerTag);
    }

    /**
     * {@inheritDoc}
     */
    public void handleCancelOk(String consumerTag) {
        LOGGER.debug("Consumer {}: Received cancel OK", consumerTag);
    }

    /**
     * {@inheritDoc}
     */
    public void handleCancel(String consumerTag) throws IOException {
        LOGGER.debug("Consumer {}: Received cancel", consumerTag);
    }

    /**
     * {@inheritDoc}
     */
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        LOGGER.debug("Consumer {}: Received shutdown signal: {}", consumerTag, sig.getMessage());
    }

    /**
     * {@inheritDoc}
     */
    public void handleRecoverOk(String consumerTag) {
        LOGGER.debug("Consumer {}: Received recover OK", consumerTag);
    }

    /**
     * <p>Handles a message delivery from the broker by converting the received
     * message parts to a {@link com.zanox.rabbiteasy.Message} which provides convenient access to
     * the message parts and hands it over to the {@link #handleMessage(com.zanox.rabbiteasy.Message)}
     * method.</p>
     *
     */
    public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
        throws IOException {
        LOGGER.debug("Consumer {}: Received handle delivery", consumerTag);
        Message message = new Message(properties)
                .exchange(envelope.getExchange())
                .routingKey(envelope.getRoutingKey())
                .deliveryTag(envelope.getDeliveryTag())
                .body(body);
        try {
            LOGGER.info("Consumer {}: Received message {}", consumerTag, envelope.getDeliveryTag());
            handleMessage(message);
        } catch (Throwable t) {
            if (!getConfiguration().isAutoAck()) {
                LOGGER.error("Consumer {}: Message {} could not be handled due to an exception during message processing", 
                    new Object[] { consumerTag, envelope.getDeliveryTag(), t });
                getChannel().basicNack(envelope.getDeliveryTag(), false, false);
                LOGGER.warn("Consumer {}: Nacked message {}",
                    new Object[] { consumerTag, envelope.getDeliveryTag(), t });
            }
            return;
        }
        if (!getConfiguration().isAutoAck()) {
            try {
                getChannel().basicAck(envelope.getDeliveryTag(), false);
                LOGGER.debug("Consumer {}: Acked message {}", consumerTag, envelope.getDeliveryTag() );
            } catch(IOException e) {
                LOGGER.error("Consumer {}: Message {} was processed but could not be acknowledged due to an exception when sending the acknowledgement", 
                    new Object[] { consumerTag, envelope.getDeliveryTag(), e });
                throw e;
            }
        }
    }

    /**
     * <p>Handles a message delivered by the broker to the consumer.</p>
     * 
     * <p>This method is expected to be overridden by extending
     * sub classes which contain the actual consumer implementation
     * and process the message.</p>
     * 
     * <p>IMPORTANT: In case the consumer is configured to acknowledge
     * messages manually the acknowledgment is handled by the super class.
     * It is sent automatically after this method returned without
     * throwing and exception. In case the method throws an exception,
     * a negative acknowledgment will be sent.
     * </p>
     * 
     * @param message The delivered message
     */
    public abstract void handleMessage(Message message);

}
