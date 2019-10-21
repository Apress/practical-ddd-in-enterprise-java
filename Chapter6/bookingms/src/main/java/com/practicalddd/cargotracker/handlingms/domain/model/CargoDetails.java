package com.practicalddd.cargotracker.handlingms.domain.model;

/**
 * Class representing the Cargo Details
 */
public class CargoDetails {

    private String bookingId;

    public CargoDetails(String bookingId){this.bookingId = bookingId;}

    public String getBookingId(){return this.bookingId;}

    public void setBookingId(String bookingId){this.bookingId = bookingId;}
}
