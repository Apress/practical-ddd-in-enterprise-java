package com.practicalddd.cargotracker.bookingms.infrastructure.brokers.rabbitmq;


import com.practicalddd.cargotracker.shareddomain.events.CargoBookedEvent;
import com.practicalddd.cargotracker.rabbitmqadaptor.EventBinder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class BookingEventBinder extends EventBinder {

    protected void bindEvents(){
        bind(CargoBookedEvent.class).toExchange("cargotracker.cargobookings")
                .withRoutingKey("cargobookings")
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
