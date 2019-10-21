package com.practicalddd.cargotracker.bookingms.domain.model;

import java.lang.invoke.MethodHandles;


import com.practicalddd.cargotracker.bookingms.domain.commands.AssignRouteToCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.commands.BookCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.commands.ChangeDestinationCommand;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoBookedEvent;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoDestinationChangedEvent;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoRoutedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;;

@Aggregate
public class Cargo {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String bookingId; // Aggregate Identifier
    private BookingAmount bookingAmount; //Booking Amount
    private Location origin; //Origin Location of the Cargo
    private RouteSpecification routeSpecification; //Route Specification of the Cargo
    private Itinerary itinerary; //Itinerary Assigned to the Cargo
    private RoutingStatus routingStatus; //Routing Status of the Cargo
    private TransportStatus transportStatus; //Transport Status of the Cargo


    protected Cargo() {
        logger.info("Empty Cargo created.");
    }

    @CommandHandler
    public Cargo(BookCargoCommand bookCargoCommand) {

        logger.info("Handling {}", bookCargoCommand);
        if(bookCargoCommand.getBookingAmount() < 0){
            throw new IllegalArgumentException("Booking Amount cannot be negative");
        }

        apply(new CargoBookedEvent(bookCargoCommand.getBookingId(),
                                    new BookingAmount(bookCargoCommand.getBookingAmount()),
                                    new Location(bookCargoCommand.getOriginLocation()),
                                    new RouteSpecification(
                                            new Location(bookCargoCommand.getOriginLocation()),
                                            new Location(bookCargoCommand.getDestLocation()),
                                            bookCargoCommand.getDestArrivalDeadline())));
    }

    /**
     * Command Handler for Assigning the Route to a Cargo
     * @param assignRouteToCargoCommand
     */

    @CommandHandler
    public void handleAssigntoRoute(AssignRouteToCargoCommand assignRouteToCargoCommand) {
        logger.info("Booking Id" +assignRouteToCargoCommand.getBookingId());
        logger.info("Assign Route to Command"+ routingStatus);
        logger.info("Assign Route to Command"+ this.routingStatus);
        if(routingStatus.equals(RoutingStatus.ROUTED)){
            throw new IllegalArgumentException("Cargo already routed");
        }
        apply( new CargoRoutedEvent(assignRouteToCargoCommand.getBookingId(),
                                    new Itinerary(assignRouteToCargoCommand.getLegs())));

    }

    /**
     * Cargo Handler for changing the Destination of a Cargo
     * @param changeDestinationCommand
     */
    @CommandHandler
    public void handleChangeDestination(ChangeDestinationCommand changeDestinationCommand){
        if(routingStatus.equals(RoutingStatus.ROUTED)){
            throw new IllegalArgumentException("Cannot change destination of a Routed Cargo");
        }

        apply(new CargoDestinationChangedEvent(changeDestinationCommand.getBookingId(),
                                new RouteSpecification(origin,
                                        new Location(changeDestinationCommand.getNewDestinationLocation()),
                                        routeSpecification.getArrivalDeadline())));

    }

    /**
     * Event Handler for the Cargo Booked Event
     * @param cargoBookedEvent
     */
    @EventSourcingHandler //Annotation indicating that the Aggregate is Event Sourced and is interested in the Cargo Booked Event raised by the Book Cargo Command
    public void on(CargoBookedEvent cargoBookedEvent) {
        logger.info("Applying {}", cargoBookedEvent);
        // State being maintained
        bookingId = cargoBookedEvent.getBookingId();
        bookingAmount = cargoBookedEvent.getBookingAmount();
        origin = cargoBookedEvent.getOriginLocation();
        routeSpecification = cargoBookedEvent.getRouteSpecification();
        routingStatus = RoutingStatus.NOT_ROUTED;
        transportStatus = TransportStatus.NOT_RECEIVED;
        logger.info("Routing Status is"+routingStatus);
    }

    /**
     * Event Handler for the Cargo Routed Event
     * @param cargoRoutedEvent
     */
    @EventSourcingHandler //Annotation indicating that the Aggregate is Event Sourced and is interested in the Cargo Routed Event raised by the Book Cargo Command
    public void on(CargoRoutedEvent cargoRoutedEvent) {
        logger.info("Applying {}", cargoRoutedEvent);
        itinerary = cargoRoutedEvent.getItinerary();
        routingStatus = RoutingStatus.ROUTED;
    }

    /**
     * Event Handler for the Change Destination Event
     * @param cargoDestinationChangedEvent
     */
    @EventSourcingHandler //Annotation indicating that the Aggregate is Event Sourced and is interested in the Cargo Booked Event raised by the Book Cargo Command
    public void on(CargoDestinationChangedEvent cargoDestinationChangedEvent) {
        logger.info("Applying {}", cargoDestinationChangedEvent);
        routingStatus = RoutingStatus.NOT_ROUTED;
        routeSpecification = cargoDestinationChangedEvent.getNewRouteSpecification();

    }

}
