package com.practicalddd.cargotracker.bookingms.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.Date;

/**
 * Implementation Class for the Booking Cargo Command
 */

public class BookCargoCommand {
    @TargetAggregateIdentifier //Identifier to indicate to Axon framework the unique instance on which the Command needs to be processed
    private String bookingId;
    private int bookingAmount;
    private String originLocation;
    private String destLocation;
    private Date destArrivalDeadline;

    public BookCargoCommand(String bookingId, int bookingAmount,
                            String originLocation, String destLocation, Date destArrivalDeadline){
        this.bookingId = bookingId;
        this.bookingAmount = bookingAmount;
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.destArrivalDeadline = destArrivalDeadline;
    }

    public void setBookingId(String bookingId){
        this.bookingId = bookingId;
    }

    public void setBookingAmount(int bookingAmount){
        this.bookingAmount = bookingAmount;
    }

    public String getBookingId(){
        return this.bookingId;
    }

    public int getBookingAmount(){
        return this.bookingAmount;
    }


    public String getOriginLocation() {return originLocation; }

    public void setOriginLocation(String originLocation) {this.originLocation = originLocation; }

    public String getDestLocation() { return destLocation; }

    public void setDestLocation(String destLocation) { this.destLocation = destLocation; }

    public Date getDestArrivalDeadline() { return destArrivalDeadline; }

    public void setDestArrivalDeadline(Date destArrivalDeadline) { this.destArrivalDeadline = destArrivalDeadline; }
}
