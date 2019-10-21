package com.practicalddd.cargotracker.booking.domain.model.aggregates;

import com.practicalddd.cargotracker.booking.domain.model.commands.BookCargoCommand;
import com.practicalddd.cargotracker.booking.domain.model.entities.Location;
import com.practicalddd.cargotracker.booking.domain.model.valueobjects.*;

import javax.persistence.*;




@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findAll",
                query = "Select c from Cargo c"),
        @NamedQuery(name = "Cargo.findByBookingId",
                query = "Select c from Cargo c where c.bookingId = :bookingId"),
        @NamedQuery(name = "Cargo.getAllBookingIds",
                query = "Select c.bookingId from Cargo c") })
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private BookingId bookingId; // Aggregate Identifier
    @Embedded
    private BookingAmount bookingAmount; //Booking Amount
    @Embedded
    private Location origin; //Origin Location of the Cargo
    @Embedded
    private RouteSpecification routeSpecification; //Route Specification of the Cargo
    @Embedded
    private CargoItinerary itinerary; //Itinerary Assigned to the Cargo
    @Embedded
    private Delivery delivery; // Checks the delivery progress of the cargo against the actual Route Specification and Itinerary
    /**
     * Default Constructor
     */
    public Cargo() {
        // Nothing to initialize.
    }

    /**
     * Constructor Command Handler for a new Cargo booking. Sets the state of the Aggregate
     * and registers the Cargo Booked Event
     *
     */
    public Cargo(BookCargoCommand bookCargoCommand) {

        this.bookingId = new BookingId(bookCargoCommand.getBookingId());
        this.routeSpecification = new RouteSpecification(
                new Location(bookCargoCommand.getOriginLocation()),
                new Location(bookCargoCommand.getDestLocation()),
                bookCargoCommand.getDestArrivalDeadline()
        );
        this.origin = routeSpecification.getOrigin();
        this.bookingAmount = new BookingAmount(bookCargoCommand.getBookingAmount());
        this.itinerary = CargoItinerary.EMPTY_ITINERARY; //Empty Itinerary since the Cargo has not been routed yet
        this.delivery = Delivery.derivedFrom(this.routeSpecification,
                this.itinerary, LastCargoHandledEvent.EMPTY);
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getOrigin() {
        return origin;
    }

    public RouteSpecification getRouteSpecification() {
        return this.routeSpecification;
    }


    public BookingAmount getBookingAmount(){
        return this.bookingAmount;
    }

    public void setBookingAmount(BookingAmount bookingAmount){
        this.bookingAmount = bookingAmount;
    }
    /**
     * @return The itinerary
     */
    public CargoItinerary getItinerary() {
        return this.itinerary;
    }

    public void setItinerary(CargoItinerary itinerary){
        this.itinerary = itinerary;
    }


    /**
     * Assigns Route to the Cargo
     * @param cargoItinerary
     */
    public void assignToRoute(CargoItinerary cargoItinerary) {
        this.itinerary = cargoItinerary;
    }


}
