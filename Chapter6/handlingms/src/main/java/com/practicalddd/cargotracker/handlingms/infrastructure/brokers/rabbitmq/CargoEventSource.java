package com.practicalddd.cargotracker.handlingms.infrastructure.brokers.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Interface depicting all output channels
 */
public interface CargoEventSource {

    @Output("cargoHandlingChannel")
    MessageChannel cargoHandling();

}
