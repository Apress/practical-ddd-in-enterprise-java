package com.practicalddd.cargotracker.rabbitmqadaptor;


import com.practicalddd.cargotracker.rabbitmqadaptor.publisher.DeliveryOptions;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * A message encapsulates the delivery content of a delivery from a broker. It wraps the body and
 * the message properties and contains commodity methods to access those in an easier fashion.
 * 
 * @author christian.bick
 * @author uwe.janner
 * 
 */
public class Message {

    private final static Logger LOGGER = LoggerFactory.getLogger(Message.class);

	public static final Charset DEFAULT_MESSAGE_CHARSET = Charset.forName("UTF-8");
	public static final int DELIVERY_MODE_PERSISTENT = 2;
    public static final String TEXT_PLAIN = "text/plain";
    public static final String APPLICATION_XML = "application/xml";

    private MessageReader messageReader;
    private MessageWriter messageWriter;

	private byte[] bodyContent = new byte[0];
	private BasicProperties basicProperties;
	private String routingKey = "";
	private String exchange = "";
	private long deliveryTag;

    public Message() {
        this(MessageProperties.BASIC);
    }

    public Message(BasicProperties basicProperties) {
        this.basicProperties = basicProperties;
        messageReader = new MessageReader(this);
        messageWriter = new MessageWriter(this);
    }

	/**
	 * Gets the basic message properties (e.g. content encoding)
	 * 
	 * @return The message properties
	 */
	public BasicProperties getBasicProperties() {
		return basicProperties;
	}

    /**
     * Gets the body content in its rare byte
     * representation.
     *
     * @return The body content as bytes
     */
    public byte[] getBodyContent() {
        return bodyContent;
    }

    /**
     * <p>Gets the message body in the representation of the
     * specified Java type.</p>
     *
     * <p>Examples:</p>
     * <p>Body as String: getBodyAs(String.class)</p>
     * <p>Body as Integer: getBodyAs(Integer.class)</p>
     * <p>Body as boolean: getBodyAs(Boolean.class)</p>
     *
     * @param type The desired Java type
     * @return The body a the given Java type.
     */
    public <T> T getBodyAs(Class<T> type) {
        return messageReader.readBodyAs(type);
    }

    /**
     * Gets the exchange to which the message is published to.
     *
     * @return The exchange
     */
	public String getExchange() {
		return this.exchange;
	}

    /**
     * Gets the routing key used to delegate messages to
     * queues bound to the set exchange. If this binding
     * matches the given routing then the message will be
     * put into the bound queue.
     *
     * @return The routing key
     */
	public String getRoutingKey() {
		return this.routingKey;
	}

    /**
     * Gets the message's delivery tag which is an identifier
     * for a message on the broker.
     *
     * @return The delivery tag
     */
	public long getDeliveryTag() {
		return deliveryTag;
	}

    /**
     * Sets the exchange to which the message is published to.
     *
     * @param exchange The exchange name
     * @return The modified message
     */
    public Message exchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    /**
     * Convenient method to send a message directly to the
     * given queue instead to an exchange. Sets the exchange
     * name to the default exchange name and sets the routing
     * key to the given queue name.
     *
     * @param queue The queue name
     * @return The modified message
     */
    public Message queue(String queue) {
        return exchange("").routingKey(queue);
    }

    /**
     * Sets the routing key used to delegate messages to
     * queues bound to the set exchange. If this binding
     * matches the given routing then the message will be
     * put into the bound queue.
     *
     * @param routingKey The routing key
     * @return The modified message
     */
    public Message routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

	/**
	 * Adds the given body content to the message.
	 *
	 * @param bodyContent The body content as bytes
	 * @return The modified message
	 */
	public Message body(byte[] bodyContent) {
		this.bodyContent = bodyContent;
		return this;
	}

    /**
     * Serializes and adds the given object as body to
     * the message using the default charset (UTF-8)
     * for encoding.
     *
     * @see MessageWriter#writeBody(Object)
     * @param body The message body object
     */
    public <T> Message body(T body) {
        messageWriter.writeBody(body);
        return this;
    }

    /**
     * Serializes and adds the given object as body to
     * the message using the given charset for encoding.
     *
     * @see MessageWriter#writeBody(Object, Charset)
     */
    public <T> Message body(T body, Charset charset) {
        messageWriter.writeBody(body, charset);
        return this;
    }

	/**
	 * <p>
	 * Flags the message to be a persistent message. A persistent message survives a total broker failure as it is
     * persisted to disc if not already delivered to and acknowledged by all consumers.
	 * </p>
	 * 
	 * <p>
	 * Important: This flag only has affect if the queues on the broker are flagged as durable.
	 * </p>
	 * 
	 * @return The modified message
	 */
	public Message persistent() {
        basicProperties = basicProperties.builder()
                .deliveryMode(DELIVERY_MODE_PERSISTENT)
                .build();
		return this;
	}

	/**
	 * <p>
	 * Sets the delivery tag for this message.
	 * </p>
	 * 
	 * <p>
	 * Note: The delivery tag is generated automatically if not set manually.
     * </p>
	 * 
	 * @param deliveryTag The delivery tag
	 * @return The modified message
	 */
	public Message deliveryTag(long deliveryTag) {
		this.deliveryTag = deliveryTag;
		return this;
	}

    /**
     * Sets the content charset encoding of this message.
     *
     * @param charset The charset encoding
     * @return The modified message
     */
    public Message contentEncoding(String charset) {
        basicProperties = basicProperties.builder()
                .contentEncoding(charset)
                .build();
        return this;
    }

    /**
     * Sets the content type of this message.
     *
     * @param contentType The content type
     * @return The modified message
     */
    public Message contentType(String contentType) {
        basicProperties = basicProperties.builder()
                .contentType(contentType)
                .build();
        return this;
    }

    /**
     * Publishes a message via the given channel.
     *
     * @param channel The channel used to publish the message
     * @throws IOException
     */
    public void publish(Channel channel) throws IOException{
        publish(channel, DeliveryOptions.NONE);
    }

    /**
     * Publishes a message via the given channel while using the specified delivery options.
     *
     * @param channel The channel used to publish the message on
     * @param deliveryOptions The delivery options to use
     * @throws IOException
     */
    public void publish(Channel channel, DeliveryOptions deliveryOptions) throws IOException {
        // Assure to have a timestamp
        if (basicProperties.getTimestamp() == null) {
            basicProperties.builder().timestamp(new Date());
        }

        boolean mandatory = deliveryOptions == DeliveryOptions.MANDATORY;
        boolean immediate = deliveryOptions == DeliveryOptions.IMMEDIATE;

        LOGGER.info("Publishing message to exchange '{}' with routing key '{}' (deliveryOptions: {}, persistent: {})",
                new Object[] { exchange, routingKey, deliveryOptions, basicProperties.getDeliveryMode() == 2 });

        channel.basicPublish(exchange, routingKey, mandatory, immediate, basicProperties, bodyContent);
        LOGGER.info("Successfully published message to exchange '{}' with routing key '{}'", exchange, routingKey);
    }

}
