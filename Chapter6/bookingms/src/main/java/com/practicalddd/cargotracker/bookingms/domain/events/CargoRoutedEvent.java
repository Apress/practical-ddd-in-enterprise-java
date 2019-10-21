package com.practicalddd.cargotracker.bookingms.domain.events;

import com.practicalddd.cargotracker.bookingms.domain.model.Itinerary;

/**
 * Event that gets generated when the Cargo is Routed
 */
public class CargoRoutedEvent {
    private String bookingId;
    private Itinerary itinerary;

    public CargoRoutedEvent(String bookingId,Itinerary itinerary){
        this.bookingId = bookingId;
        this.itinerary = itinerary;
    }

    public String getBookingId(){ return this.bookingId; }

    public Itinerary getItinerary(){ return this.itinerary; }
}
