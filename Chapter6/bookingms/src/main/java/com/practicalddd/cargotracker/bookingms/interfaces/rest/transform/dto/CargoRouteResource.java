package com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto;

import java.util.List;

public class CargoRouteResource {

    private String bookingId;

    private List<Leg> legs;

    public CargoRouteResource(String bookingId,List<Leg> legs){
        this.setBookingId(bookingId);
        this.setLegs(legs);
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
