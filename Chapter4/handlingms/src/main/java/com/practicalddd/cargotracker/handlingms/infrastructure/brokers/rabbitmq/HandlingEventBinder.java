package com.practicalddd.cargotracker.handlingms.infrastructure.brokers.rabbitmq;

import com.practicalddd.cargotracker.shareddomain.events.CargoHandledEvent;
import com.practicalddd.cargotracker.rabbitmqadaptor.EventBinder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class HandlingEventBinder extends EventBinder {

    protected void bindEvents(){
        bind(CargoHandledEvent.class).toExchange("cargotracker.cargohandlings")
                .withRoutingKey("cargohandlings")
                .withPersistentMessages()
                .withPublisherConfirms();
    }

    @PostConstruct
    public void initialize(){
        try{
            super.initialize();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
