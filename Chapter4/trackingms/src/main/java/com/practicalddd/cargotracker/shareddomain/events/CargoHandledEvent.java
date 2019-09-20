package com.practicalddd.cargotracker.shareddomain.events;

import com.practicalddd.cargotracker.rabbitmqadaptor.ContainsContent;

import javax.enterprise.context.Dependent;

@Dependent
public class CargoHandledEvent implements ContainsContent<CargoHandledEventData> {

    private CargoHandledEventData cargoHandledEventData;
    public void setContent(CargoHandledEventData cargoHandledEventData) { this.cargoHandledEventData = cargoHandledEventData; }
    public CargoHandledEventData getContent() {
        return cargoHandledEventData;
    }
}
