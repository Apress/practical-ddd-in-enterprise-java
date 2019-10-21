package com.practicalddd.cargotracker.booking.domain.model.commands;

import java.util.Date;

/**
 * Book Cargo Command class
 */
public class BookCargoCommand {
    private String bookingId;
    private int bookingAmount;
    private String originLocation;
    private String destLocation;
    private Date destArrivalDeadline;

    public BookCargoCommand(){}

    public BookCargoCommand(int bookingAmount,
                            String originLocation, String destLocation, Date destArrivalDeadline){

        this.bookingAmount = bookingAmount;
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.destArrivalDeadline = destArrivalDeadline;
    }

    public void setBookingId(String bookingId){ this.bookingId = bookingId; }

    public String getBookingId(){return this.bookingId;}
    public void setBookingAmount(int bookingAmount){
        this.bookingAmount = bookingAmount;
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
