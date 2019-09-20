package com.practicalddd.cargotracker.bookingms.domain.model.commands;

import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.Cargo;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.CargoItinerary;

import java.util.Date;

/**
 * Command Class to assign a route to a booked cargo
 */
public class RouteCargoCommand {

    private String cargoBookingId;
    private String originLocation;
    private String destinationLocation;
    private Date arrivalDeadline;
    private CargoItinerary cargoItinerary;
    public RouteCargoCommand(){ }

    public RouteCargoCommand(String cargoBookingId,
                             String originLocation,String destinationLocation,Date arrivalDeadline){
        this.setCargoBookingId(cargoBookingId);
        this.setOriginLocation(originLocation);
        this.setDestinationLocation(destinationLocation);
        this.setArrivalDeadline(arrivalDeadline);
    }


    public String getCargoBookingId() {
        return cargoBookingId;
    }

    public void setCargoBookingId(String cargoBookingId) {
        this.cargoBookingId = cargoBookingId;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Date getArrivalDeadline() {
        return arrivalDeadline;
    }

    public void setArrivalDeadline(Date arrivalDeadline) {
        this.arrivalDeadline = arrivalDeadline;
    }

    public void setCargoItinerary(CargoItinerary cargoItinerary){this.cargoItinerary = cargoItinerary;}

    public CargoItinerary getCargoItinerary(){return this.cargoItinerary;}
}
