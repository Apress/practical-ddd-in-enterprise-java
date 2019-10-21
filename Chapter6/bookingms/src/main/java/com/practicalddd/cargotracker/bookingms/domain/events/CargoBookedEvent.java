package com.practicalddd.cargotracker.bookingms.domain.events;

import com.practicalddd.cargotracker.bookingms.domain.model.BookingAmount;
import com.practicalddd.cargotracker.bookingms.domain.model.Location;
import com.practicalddd.cargotracker.bookingms.domain.model.RouteSpecification;


/**
 * Event resulting from the Cargo Booking Command
 */
public class CargoBookedEvent
{
    private String bookingId;
    private BookingAmount bookingamount;
    private Location originLocation;
    private RouteSpecification routeSpecification;
    public CargoBookedEvent(String bookingId,
                            BookingAmount bookingAmount,Location originLocation, RouteSpecification routeSpecification){
        this.bookingId = bookingId;
        this.bookingamount = bookingAmount;
        this.originLocation = originLocation;
        this.routeSpecification = routeSpecification;
    }

    public String getBookingId(){ return this.bookingId; }
    public BookingAmount getBookingAmount(){ return this.bookingamount; }
    public Location getOriginLocation(){return this.originLocation;}
    public RouteSpecification getRouteSpecification(){return this.routeSpecification;}
}
