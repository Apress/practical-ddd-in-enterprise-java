package com.practicalddd.cargotracker.bookingms.domain.model.aggregates;

import javax.persistence.*;

import com.practicalddd.cargotracker.bookingms.domain.model.commands.BookCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.model.commands.RouteCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.model.entities.Location;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.LastCargoHandledEvent;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.*;
import com.practicalddd.cargotracker.shareddomain.events.CargoBookedEvent;
import com.practicalddd.cargotracker.shareddomain.events.CargoBookedEventData;
import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEventData;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findAll",
                query = "Select c from Cargo c"),
        @NamedQuery(name = "Cargo.findByBookingId",
                query = "Select c from Cargo c where c.bookingId = :bookingId"),
        @NamedQuery(name = "Cargo.findAllBookingIds",
                query = "Select c.bookingId from Cargo c") })
public class Cargo extends AbstractAggregateRoot<Cargo> {
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
    public Cargo(BookCargoCommand bookCargoCommand){
        this.bookingId = new BookingId(bookCargoCommand.getBookingId());
        this.routeSpecification = new RouteSpecification(
                    new Location(bookCargoCommand.getOriginLocation()),
                    new Location(bookCargoCommand.getDestLocation()),
                    bookCargoCommand.getDestArrivalDeadline()
            );
        this.origin = routeSpecification.getOrigin();
        this.itinerary = CargoItinerary.EMPTY_ITINERARY; //Empty Itinerary since the Cargo has not been routed yet
        this.bookingAmount = new BookingAmount(bookCargoCommand.getBookingAmount());
        this.delivery = Delivery.derivedFrom(this.routeSpecification,
                this.itinerary, LastCargoHandledEvent.EMPTY);

        //Add this domain event which needs to be fired when the new cargo is saved
        addDomainEvent(new
                CargoBookedEvent(
                        new CargoBookedEventData(bookingId.getBookingId())));
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

    /**
     * Command Handler for the Route Cargo Command. Sets the state of the Aggregate and registers the
     * Cargo routed event
     * @param routeCargoCommand
     */

    public void assignToRoute(RouteCargoCommand routeCargoCommand) {
        this.itinerary = routeCargoCommand.getCargoItinerary();
        // Handling consistency within the Cargo aggregate synchronously
        this.delivery = delivery.updateOnRouting(this.routeSpecification,
                this.itinerary);

        //Add this domain event which needs to be fired when the new cargo is saved
        addDomainEvent(new
                CargoRoutedEvent(
                new CargoRoutedEventData(bookingId.getBookingId())));
    }


    /**
     *
     * @param lastCargoHandledEvent
     */
    public void deriveDeliveryProgress(LastCargoHandledEvent lastCargoHandledEvent) {
        this.delivery = Delivery.derivedFrom(getRouteSpecification(), getItinerary(),
                lastCargoHandledEvent);
    }

    /**
     * Method to register the event
     * @param event
     */
    public void addDomainEvent(Object event){
        registerEvent(event);
    }


}
