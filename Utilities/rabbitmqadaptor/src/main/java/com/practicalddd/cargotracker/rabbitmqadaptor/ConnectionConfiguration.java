package com.practicalddd.cargotracker.rabbitmqadaptor;

import com.rabbitmq.client.ConnectionFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConnectionConfiguration {
    public static final String PROFILE_PROPERTY = "rabbiteasy.profile";
    public static final String DEFAULT_PROFILE = "";

    /**
     * <p>The profile name of this configuration. If a profile is specified, this
     * configuration is only used if the system property
     * {@link ConnectionConfiguration#PROFILE_PROPERTY} is set to the specified value (e.g. test, staging, ...).</p>
     */
    String profile() default DEFAULT_PROFILE;

    String host() default ConnectionFactory.DEFAULT_HOST;
    String virtualHost() default ConnectionFactory.DEFAULT_VHOST;
    int port() default ConnectionFactory.DEFAULT_AMQP_PORT;
    int timeout() default ConnectionFactory.DEFAULT_CONNECTION_TIMEOUT;
    int heartbeat() default ConnectionFactory.DEFAULT_HEARTBEAT;
    int frameMax() default ConnectionFactory.DEFAULT_FRAME_MAX;
    String username() default ConnectionFactory.DEFAULT_USER;
    String password() default ConnectionFactory.DEFAULT_PASS;
}
