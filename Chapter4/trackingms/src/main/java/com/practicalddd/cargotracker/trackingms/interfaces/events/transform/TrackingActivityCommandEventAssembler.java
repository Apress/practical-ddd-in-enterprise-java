package com.practicalddd.cargotracker.trackingms.interfaces.events.transform;

import com.practicalddd.cargotracker.shareddomain.events.CargoHandledEvent;
import com.practicalddd.cargotracker.shareddomain.events.CargoHandledEventData;
import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.trackingms.domain.model.commands.AddTrackingEventCommand;
import com.practicalddd.cargotracker.trackingms.domain.model.commands.AssignTrackingNumberCommand;

/**
 * Assembler class to convert the Cargo Routed Event to the Assign Tracking Number Command Model
 */
public class TrackingActivityCommandEventAssembler {

    /**
     * Static method within the Assembler class
     * @param cargoHandledEvent
     * @return AssignTrackingNumberCommand Model
     */
    public static AddTrackingEventCommand toCommandFromEvent(CargoHandledEvent cargoHandledEvent){
        CargoHandledEventData eventData = cargoHandledEvent.getContent();
        return new AddTrackingEventCommand(
                eventData.getBookingId(),
                eventData.getHandlingCompletionTime(),
                eventData.getHandlingType(),
                eventData.getHandlingLocation(),
                eventData.getVoyageNumber());
    }
}
