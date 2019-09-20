package com.practicalddd.cargotracker.trackingms.infrastructure.brokers.rabbitmq;

import com.practicalddd.cargotracker.shareddomain.events.CargoHandledEvent;
import com.practicalddd.cargotracker.rabbitmqadaptor.EventBinder;


import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class HandlingEventBinder extends EventBinder {

    protected void bindEvents(){
        bind(CargoHandledEvent.class).toQueue("cargotracker.handlingqueue");
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
