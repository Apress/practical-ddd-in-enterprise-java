package com.practicalddd.cargotracker.rabbitmqadaptor.publisher;


/**
 * Indicates the reliability with which a publisher
 * publishes messages.
 *
 * @author christian.bick
 */
public enum PublisherReliability {
    /**
     * No reliability at all (fire-and-forget)
     *
     * @see SimplePublisher
     */
    NONE,
    /**
     * Single messages are confirmed to be received by the broker
     *
     * @see ConfirmedPublisher
     */
    CONFIRMED,
    /**
     * One or several messages are sent in an transaction
     *
     * @see TransactionalPublisher
     */
    TRANSACTIONAL
}
