package com.practicalddd.cargotracker.trackingms.infrastructure.brokers.rabbitmq;

import com.practicalddd.cargotracker.shareddomain.events.CargoBookedEvent;
import com.practicalddd.cargotracker.rabbitmqadaptor.EventBinder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class BookingEventBinder extends EventBinder {

    protected void bindEvents(){
        bind(CargoBookedEvent.class).toQueue("cargotracker.bookingsqueue");
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
