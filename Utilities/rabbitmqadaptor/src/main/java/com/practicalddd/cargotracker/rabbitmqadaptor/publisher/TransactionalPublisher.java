package com.practicalddd.cargotracker.rabbitmqadaptor.publisher;


import com.practicalddd.cargotracker.rabbitmqadaptor.Message;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * <p>A transactional publisher sends messages to a broker
 * within a transaction scope. A message is only put into
 * its destination queues when the transaction is committed</p>
 * 
 * @author christian.bick
 * @author uwe.janner
 * @author soner.dastan
 *
 */
public class TransactionalPublisher extends DiscretePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalPublisher.class);

    public TransactionalPublisher(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * {@inheritDoc}
     */
    public void publish(Message message, DeliveryOptions deliveryOptions) throws IOException, TimeoutException {
        publish(Collections.<Message>singletonList(message), deliveryOptions);
    }

    /**
     * {@inheritDoc}
     */
    public void publish(List<Message> messages, DeliveryOptions deliveryOptions) throws IOException,TimeoutException {
        for (int attempt = 1; attempt <= DEFAULT_RETRY_ATTEMPTS; attempt++) {
            if (attempt > 1) {
                LOGGER.info("Attempt {} to send messages within transaction", attempt);
            }

            try {
                Channel channel = provideChannel();
                try {
                    for (Message message : messages) {
                        message.publish(channel, deliveryOptions);
                    }
                    commitTransaction(channel);
                } catch (IOException e) {
                    rollbackTransaction(channel);
                    throw e;
                }
                return;
            } catch (IOException e) {
                handleIoException(attempt, e);
            }
        }
    }

    @Override
    protected Channel provideChannel() throws IOException, TimeoutException {
        Channel channel = super.provideChannel();
        channel.txSelect();
        return  channel;
    }

    static void commitTransaction(Channel channel) throws IOException {
        try {
            LOGGER.info("Committing transaction");
            channel.txCommit();
            LOGGER.info("Transaction committed");
        } catch (IOException e) {
            LOGGER.error("Failed to commit transaction", e);
            throw e;
        }
    }

    static void rollbackTransaction(Channel channel) throws IOException {
        try {
            LOGGER.info("Rolling back transaction");
            channel.txRollback();
            LOGGER.info("Transaction rolled back");
        } catch (IOException e) {
            LOGGER.error("Failed to roll back transaction", e);
            throw e;
        }
    }
}
