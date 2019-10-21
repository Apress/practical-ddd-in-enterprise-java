package com.practicalddd.cargotracker.shareddomain.events;


/**
 * Event Class for the Cargo Booked Event. Wraps up the Cargo Booking identifier
 * for the event
 */

public class CargoBookedEvent {
    private CargoBookedEventData cargoBookedEventData;
    private CargoBookedEvent(){}
    public CargoBookedEvent(CargoBookedEventData cargoBookedEventData){
        this.cargoBookedEventData  = cargoBookedEventData;
    }
}
