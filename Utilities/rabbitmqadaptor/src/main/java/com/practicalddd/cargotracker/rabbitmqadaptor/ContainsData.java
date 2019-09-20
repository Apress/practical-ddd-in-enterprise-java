package com.practicalddd.cargotracker.rabbitmqadaptor;


/**
 * <p>Events that implement this interface are enabled to carry
 * arbitrary data within an event. The carried data is set as
 * AMQP message body.</p>
 *
 * <p>This interface is intended to be used for transporting
 * binary files within an event. For transporting content
 * which is represented by serializable Java types use
 * {@link ContainsContent} or {@link ContainsId}.</p>
 *
 * @see ContainsContent
 * @see ContainsId
 *
 * @author christian.bick
 */
public interface ContainsData {
    void setData(byte[] data);
    byte[] getData();
}
