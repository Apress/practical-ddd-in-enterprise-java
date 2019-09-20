package com.practicalddd.cargotracker.bookingms.infrastructure.brokers.rabbitmq;


import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.rabbitmqadaptor.EventBinder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class RoutingEventBinder extends EventBinder {

    protected void bindEvents(){
        bind(CargoRoutedEvent.class).toExchange("cargotracker.cargoroutings")
                .withRoutingKey("cargoroutings")
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
