package com.practicalddd.cargotracker.booking.interfaces.rest.dto;

import java.time.LocalDate;

/**
 * Resource class for the Book Cargo Command API
 */
public class BookCargoResource {

    private int bookingAmount;
    private String originLocation;
    private String destLocation;
    private LocalDate destArrivalDeadline;

    public BookCargoResource(){}

    public BookCargoResource(int bookingAmount,
                             String originLocation, String destLocation, LocalDate destArrivalDeadline){

        this.bookingAmount = bookingAmount;
        this.originLocation = originLocation;
        this.destLocation = destLocation;
        this.destArrivalDeadline = destArrivalDeadline;
    }


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

    public LocalDate getDestArrivalDeadline() { return destArrivalDeadline; }

    public void setDestArrivalDeadline(LocalDate destArrivalDeadline) { this.destArrivalDeadline = destArrivalDeadline; }

}
