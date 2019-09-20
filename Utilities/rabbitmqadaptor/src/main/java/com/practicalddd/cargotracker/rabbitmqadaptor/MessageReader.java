package com.practicalddd.cargotracker.rabbitmqadaptor;


import com.rabbitmq.client.AMQP.BasicProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * Provides utilities for an easy access of message
 * content.
 * 
 * @author christian.bick
 *
 */
public class MessageReader {

    private Message message;

    public MessageReader(Message message) {
        this.message = message;
    }

    /**
     * Extracts the message body's charset encoding
     * by looking it up in the message properties if
     * given there and choosing the default charset (UTF-8)
     * otherwise.
     *
     * @return The message body's charset encoding
     */
    public Charset readCharset() {
        BasicProperties basicProperties = message.getBasicProperties();
        if (basicProperties == null) {
            return Message.DEFAULT_MESSAGE_CHARSET;
        }
        String contentCharset = basicProperties.getContentEncoding();
        if (contentCharset == null) {
            return Message.DEFAULT_MESSAGE_CHARSET;
        }
        return Charset.forName(contentCharset);
    }

    /**
     * Extracts the message body and interprets it
     * as the given Java type
     *
     * @param type The Java type
     * @return The message body a the specified type
     */
    @SuppressWarnings("unchecked")
    public <T> T readBodyAs(Class<T> type) {
        if (String.class.isAssignableFrom(type)) {
            return (T)readBodyAsString();
        } else if (Number.class.isAssignableFrom(type)) {
            return (T)readBodyAsNumber((Class<Number>)type);
        } else if (Boolean.class.isAssignableFrom(type)) {
            return (T)readBodyAsBoolean();
        } else if (Character.class.isAssignableFrom(type)) {
            return (T)readBodyAsChar();
        }
        return readBodyAsObject(type);
    }

    /**
     * Extracts the message body and interprets it
     * as a string.
     *
     * @return The message body as string
     */
    public String readBodyAsString() {
        Charset charset = readCharset();
        byte[] bodyContent = message.getBodyContent();
        return new String(bodyContent, charset);
    }

    /**
     * Extracts the message body and interprets it
     * as a boolean.
     *
     * @return The message body as boolean
     */
    public Boolean readBodyAsBoolean() {
        String messageContent = readBodyAsString();
        return Boolean.valueOf(messageContent);
    }

    /**
     * Extracts the message body and interprets it
     * as a single character.
     *
     * @return Tje message body as character
     */
    public Character readBodyAsChar() {
        String messageContent = readBodyAsString();
        return messageContent.charAt(0);
    }

    /**
     * Extracts the message body and interprets it
     * as the given number (e.g. Integer) .
     *
     * @param type The number type
     * @return The message body as the specified number
     */
    @SuppressWarnings("unchecked")
    public <T extends Number> T readBodyAsNumber(Class<T> type) {
        String messageContent = readBodyAndValidateForNumber();
        if (type.equals(BigDecimal.class)) {
            return (T)new BigDecimal(messageContent);
        } else if (type.equals(BigInteger.class)) {
            return (T)new BigInteger(messageContent);
        } else if (type.equals(Byte.class)) {
            return (T)Byte.valueOf(messageContent);
        } else if (type.equals(Short.class)) {
            return (T)Short.valueOf(messageContent);
        } else if (type.equals(Integer.class)) {
            return (T)Integer.valueOf(messageContent);
        } else if (type.equals(Long.class)) {
            return (T)Long.valueOf(messageContent);
        } else {
            throw new RuntimeException("Unsupported number format: " + type);
        }
    }

    /**
     * Extracts the message body and interprets it as
     * the XML representation of an object of the given
     * type.
     *
     * @param type The type (class) of the object
     * @return The message body as an object of the specified type
     */
    @SuppressWarnings("unchecked")
    public <T> T readBodyAsObject(Class<T> type) {
        Charset charset = readCharset();
        InputStream inputStream = new ByteArrayInputStream(message.getBodyContent());
        InputStreamReader inputReader = new InputStreamReader(inputStream, charset);
        StreamSource streamSource = new StreamSource(inputReader);
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
            if (type.isAnnotationPresent(XmlRootElement.class)) {
                return (T)unmarshaller.unmarshal(streamSource);
            } else {
                JAXBElement<T> element = unmarshaller.unmarshal(streamSource, type);
                return element.getValue();
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts the charset of the message content. If
     * no charset is provided in the message properties
     * then the default charset (UTF-8) is used.
     * 
     * @return The message content charset
     */
    String readBodyAndValidateForNumber() {
        String messageContent = readBodyAsString();
        if (messageContent == null || messageContent.isEmpty()) {
            throw new RuntimeException("Message is empty");
        }
        // Check if content is a number
        for (int i = 0; i < messageContent.length(); i++) {
            if (! Character.isDigit(messageContent.charAt(i))) {
                throw new RuntimeException("Message is not a number");
            }
        }
        return messageContent;
    }

}
