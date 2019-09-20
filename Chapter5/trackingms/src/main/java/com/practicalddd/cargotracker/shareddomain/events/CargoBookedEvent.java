package com.practicalddd.cargotracker.shareddomain.events;


/**
 * Event Class for the Cargo Booked Event. Wraps up the Cargo Booked Event Data
 */

public class CargoBookedEvent {

    CargoBookedEventData cargoBookedEventData;
    public CargoBookedEvent(){}
    public CargoBookedEvent(CargoBookedEventData cargoBookedEventData){
        this.cargoBookedEventData = cargoBookedEventData;
    }

    public void setCargoBookedEventData(CargoBookedEventData cargoBookedEventData){this.cargoBookedEventData = cargoBookedEventData;}
    public CargoBookedEventData getCargoBookedEventData(){
        return cargoBookedEventData;
    }
}
