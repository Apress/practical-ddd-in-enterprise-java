package com.practicalddd.cargotracker.trackingms.interfaces.events;


import com.practicalddd.cargotracker.trackingms.application.internal.commandgateways.TrackingProjectionService;
import com.practicalddd.cargotracker.trackingms.domain.events.CargoTrackedEvent;
import com.practicalddd.cargotracker.trackingms.domain.projections.BookingId;
import com.practicalddd.cargotracker.trackingms.domain.projections.TrackingNumber;
import com.practicalddd.cargotracker.trackingms.domain.projections.TrackingProjection;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * Event Handlers for all events raised by the Cargo Aggregate
 */
@Service
public class TrackingProjectionsEventHandler {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private TrackingProjectionService trackingProjectionService; //Dependencies

    public TrackingProjectionsEventHandler(TrackingProjectionService trackingProjectionService){
        this.trackingProjectionService = trackingProjectionService;
    }

    /**
     * Event Handler for  Tracking
     * @param cargoTrackedEvent
     */
    @EventHandler
    public void cargoTrackedEventHandler(CargoTrackedEvent cargoTrackedEvent) {
        logger.info("Applying {}", cargoTrackedEvent.getBookingId());

        TrackingProjection trackingProjection = new TrackingProjection(
            new BookingId(cargoTrackedEvent.getBookingId()),
                new TrackingNumber(cargoTrackedEvent.getTrackingId())
        );
        trackingProjectionService.assignTrackingNumberToCargo(trackingProjection);
    }


}
