package com.practicalddd.cargotracker.rabbitmqadaptor;


import com.rabbitmq.client.ConnectionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ConnectionConfigurator {

    @Inject
    ConnectionFactory connectionFactory;

    /**
     * Configures the connection factory supplied by dependency injection using the
     * connection configuration annotated at the given class.
     *
     * @param clazz The class annotated with the connection configuration.
     */
    public void configureFactory(Class<?> clazz) {
        ConnectionConfiguration connectionConfiguration = resolveConnectionConfiguration(clazz);
        if (connectionConfiguration == null) {
            return;
        }
        connectionFactory.setHost(connectionConfiguration.host());
        connectionFactory.setVirtualHost(connectionConfiguration.virtualHost());
        connectionFactory.setPort(connectionConfiguration.port());
        connectionFactory.setConnectionTimeout(connectionConfiguration.timeout());
        connectionFactory.setRequestedHeartbeat(connectionConfiguration.heartbeat());
        connectionFactory.setUsername(connectionConfiguration.username());
        connectionFactory.setPassword(connectionConfiguration.password());
        connectionFactory.setRequestedFrameMax(connectionConfiguration.frameMax());
    }

    ConnectionConfiguration resolveConnectionConfiguration(Class<?> clazz) {
        // First, look up if there is a single connection configuration
        ConnectionConfiguration connectionConfiguration = clazz.getAnnotation(ConnectionConfiguration.class);
        if (connectionConfiguration != null) {
            return connectionConfiguration;
        }
        // Otherwise, look up if there are multiple connection configurations
        ConnectionConfigurations connectionConfigurations = clazz.getAnnotation(ConnectionConfigurations.class);
        // If so, iterate over the connection configurations and return the configuration with the profile
        // matching the one set in the system property
        if (connectionConfigurations != null) {
            // Get the system property, assume the default profile if the system property is not set
            String profileProperty = System.getProperty(ConnectionConfiguration.PROFILE_PROPERTY);
            String profile = profileProperty == null ? ConnectionConfiguration.DEFAULT_PROFILE : profileProperty;
            for (ConnectionConfiguration connectionConfigurationCandidate : connectionConfigurations.value()) {
                if (connectionConfigurationCandidate.profile().equalsIgnoreCase(profile)) {
                    return connectionConfigurationCandidate;
                }
            }
        }
        return null;
    }
}
