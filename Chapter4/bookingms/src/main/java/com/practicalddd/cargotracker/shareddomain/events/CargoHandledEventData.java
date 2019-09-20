package com.practicalddd.cargotracker.shareddomain.events;

import java.util.Date;

/**
 * Event Data for the Cargo Handled Event
 */
public class CargoHandledEventData {
    private String bookingId;
    private String handlingType;
    private Date handlingCompletionTime;
    private String handlingLocation;
    private String voyageNumber;

    public CargoHandledEventData(String bookingId,String handlingType,Date handlingCompletionTime,String handlingLocation,String voyageNumber){
        this.bookingId = bookingId;
        this.handlingType = handlingType;
        this.handlingCompletionTime = handlingCompletionTime;
        this.handlingLocation = handlingLocation;
        this.voyageNumber = voyageNumber;
    }

    public CargoHandledEventData(){}

    public String getBookingId(){return this.bookingId;}
    public String getHandlingType(){return this.handlingType;}
    public Date getHandlingCompletionTime(){return this.handlingCompletionTime;}
    public String getHandlingLocation(){return this.handlingLocation;}
    public String getVoyageNumber(){return this.voyageNumber;}
}
