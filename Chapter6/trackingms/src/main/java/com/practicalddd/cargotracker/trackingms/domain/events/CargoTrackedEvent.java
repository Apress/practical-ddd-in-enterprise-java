package com.practicalddd.cargotracker.trackingms.domain.events;



/**
 * Event resulting from the Cargo being tracked
 */
public class CargoTrackedEvent
{
    private String bookingId;
    private String trackingId;

    public CargoTrackedEvent(String bookingId,
                             String trackingId){
        this.bookingId = bookingId;
        this.trackingId = trackingId;

    }

    public String getBookingId(){ return this.bookingId; }
    public String getTrackingId(){return this.trackingId;}
}
