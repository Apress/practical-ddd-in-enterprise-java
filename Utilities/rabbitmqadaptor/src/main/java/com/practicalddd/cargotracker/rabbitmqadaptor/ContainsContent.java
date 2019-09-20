package com.practicalddd.cargotracker.rabbitmqadaptor;


/**
 * <p>Events that implement this interface are enabled to carry
 * arbitrary content within an event. The carried content is
 * automatically (de-)serialized by the framework from/to an
 * AMQP message body.</p>
 *
 * <p>Primitive types and Strings are (de-)serialized from/to their
 * textual representation. Complex types are (de-)serialized in
 * their XML representation.</p>
 *
 * @author christian.bick
 */
public interface ContainsContent<T> {
    void setContent(T content);
    T getContent();
}
