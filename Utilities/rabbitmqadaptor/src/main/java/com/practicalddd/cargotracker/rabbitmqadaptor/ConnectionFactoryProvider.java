package com.practicalddd.cargotracker.rabbitmqadaptor;

import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 * Provides the default connection factory.
 *
 * @author christian.bick
 */
public class ConnectionFactoryProvider {

    @Produces
    @Singleton
    public ConnectionFactory provideConnectionFactory() {
        return new SingleConnectionFactory();
    }
}
