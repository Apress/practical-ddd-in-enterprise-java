package com.practicalddd.cargotracker.rabbitmqadaptor;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * @author christian.bick
 */
public class MessageWriter {

    private Message message;

    public MessageWriter(Message message) {
        this.message = message;
    }

    /**
     * <p>Writes the message body using the given object of type T
     * using the default charset UTF-8.</p>
     *
     * @see #writeBody(Object,Charset)
     * @param body The message body
     * @param <T> The object type
     */
    public <T> void writeBody(T body) {
        writeBody(body, Message.DEFAULT_MESSAGE_CHARSET);
    }

    /**
     * <p>Writes the message body using the given object of type T
     * using the given charset for character encoding.</p>
     *
     * <p>For primitive types and Strings, the message body is written as
     * plain text. In all other cases, the object is serialized to XML.</p>
     *
     * @param body The message body
     * @param charset The charset to use for character encoding
     * @param <T> The object type
     */
    public <T> void writeBody(T body, Charset charset) {
        if (isPrimitive(body)) {
            String bodyAsString = String.valueOf(body);
            writeBodyFromString(bodyAsString, charset);
        } else if (isString(body)) {
            String bodyAsString = (String)body;
            writeBodyFromString(bodyAsString, charset);
        } else {
            writeBodyFromObject(body, charset);
        }
    }

    /**
     * Writes the message body from a string using the given charset
     * as encoding and setting the content type to text/plain.
     *
     * @param bodyAsString Body to write as string
     * @param charset The charset to encode the string
     */
    public void writeBodyFromString(String bodyAsString, Charset charset) {
        message.contentEncoding(charset.name())
                .contentType(Message.TEXT_PLAIN);
        byte[] bodyContent = bodyAsString.getBytes(charset);
        message.body(bodyContent);
    }

    /**
     * Writes the body by serializing the given object to XML.
     *
     * @param bodyAsObject The body as object
     * @param <T> The object type
     */
    public <T> void writeBodyFromObject(T bodyAsObject, Charset charset) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>)bodyAsObject.getClass();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer outputWriter = new OutputStreamWriter(outputStream, charset);

        try {
            Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
            if (clazz.isAnnotationPresent(XmlRootElement.class)) {
                marshaller.marshal(bodyAsObject, outputWriter);
            } else {
                String tagName = unCapitalizedClassName(clazz);
                JAXBElement<T> element = new JAXBElement<T>(new QName("", tagName), clazz, bodyAsObject);
                marshaller.marshal(element, outputWriter);
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        byte[] bodyContent = outputStream.toByteArray();
        message.contentType(Message.APPLICATION_XML)
                .contentEncoding(charset.name());
        message.body(bodyContent);
    }

    boolean isString(Object object) {
        return object instanceof String;
    }

    boolean isPrimitive(Object object) {
        return object.getClass().isPrimitive()
                || object instanceof Boolean
                || object instanceof Character
                || object instanceof Number;
    }

    String unCapitalizedClassName(Class<?> clazz)  {
        String className = clazz.getSimpleName();
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

}
