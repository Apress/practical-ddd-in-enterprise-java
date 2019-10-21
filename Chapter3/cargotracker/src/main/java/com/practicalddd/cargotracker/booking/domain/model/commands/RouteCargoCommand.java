package com.practicalddd.cargotracker.booking.domain.model.commands;



/**
 * Command Class to assign a route to a booked cargo
 */
public class RouteCargoCommand {
    private String cargoBookingId;

    public RouteCargoCommand(){ }

    public RouteCargoCommand(String cargoBookingId){
        this.setCargoBookingId(cargoBookingId);
    }


    public String getCargoBookingId() {
        return cargoBookingId;
    }

    public void setCargoBookingId(String cargoBookingId) {
        this.cargoBookingId = cargoBookingId;
    }


}
