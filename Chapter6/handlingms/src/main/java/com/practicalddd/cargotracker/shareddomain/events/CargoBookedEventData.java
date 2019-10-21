package com.practicalddd.cargotracker.shareddomain.events;


/**
 * Event Data for the Cargo Booked Event
 */
public class CargoBookedEventData {

    private String bookingId;

    public CargoBookedEventData(){}
    public CargoBookedEventData(String bookingId){ this.bookingId = bookingId; }
    public String getBookingId(){return this.bookingId;}

}
