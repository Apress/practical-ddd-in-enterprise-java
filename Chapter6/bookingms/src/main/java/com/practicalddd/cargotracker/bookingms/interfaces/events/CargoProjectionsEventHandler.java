package com.practicalddd.cargotracker.bookingms.interfaces.events;

import com.practicalddd.cargotracker.bookingms.application.internal.querygateways.CargoProjectionService;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoBookedEvent;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoDestinationChangedEvent;
import com.practicalddd.cargotracker.bookingms.domain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.bookingms.domain.model.RoutingStatus;
import com.practicalddd.cargotracker.bookingms.domain.projections.CargoSummary;
import com.practicalddd.cargotracker.handlingms.domain.events.HandlingActivityRegisteredEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * Event Handlers for all events raised by the Cargo Aggregate
 */
@Service
public class CargoProjectionsEventHandler {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CargoProjectionService cargoProjectionService; //Dependencies

    public CargoProjectionsEventHandler(CargoProjectionService cargoProjectionService){
        this.cargoProjectionService = cargoProjectionService;
    }
    /**
     * EVent Handler for the Cargo Booked Event. Converts the Event Data to the corresponding Aggregate Projection Model
     * and delegates to the Application Service to process it further
     * @param cargoBookedEvent
     * @param eventTimestamp
     */
    @EventHandler
    public void cargoBookedEventHandler(CargoBookedEvent cargoBookedEvent) {
        logger.info("Applying {}", cargoBookedEvent.getBookingId());

        CargoSummary cargoSummary = new CargoSummary(cargoBookedEvent.getBookingId(),"",RoutingStatus.NOT_ROUTED,
                "","",new java.util.Date());
        cargoProjectionService.storeCargoSummary(cargoSummary);
    }

    /**
     * Event Handler for the Cargo Routed Event
     * @param cargoRoutedEvent
     */
    @EventHandler
    public void cargoRoutedEventhandler(CargoRoutedEvent cargoRoutedEvent){
        logger.info("Applying {}",cargoRoutedEvent.getBookingId());
        CargoSummary cargoSummary = cargoProjectionService.getCargoSummary(cargoRoutedEvent.getBookingId());
        cargoSummary.setRouting_status(RoutingStatus.ROUTED);

    }


    @EventHandler
    public void cargoHandledEvent(HandlingActivityRegisteredEvent HandlingActivityRegisteredEvent){
        logger.info("Cargo was handled");
    }
}
