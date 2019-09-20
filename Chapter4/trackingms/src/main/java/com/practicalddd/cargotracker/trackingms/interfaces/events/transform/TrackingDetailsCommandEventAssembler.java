package com.practicalddd.cargotracker.trackingms.interfaces.events.transform;

import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.trackingms.domain.model.commands.AssignTrackingNumberCommand;

/**
 * Assembler class to convert the Cargo Routed Event to the Assign Tracking Number Command Model
 */
public class TrackingDetailsCommandEventAssembler {

    /**
     * Static method within the Assembler class
     * @param cargoRoutedEvent
     * @return AssignTrackingNumberCommand Model
     */
    public static AssignTrackingNumberCommand toCommandFromEvent(CargoRoutedEvent cargoRoutedEvent){
        System.out.println("****cargoRoutedEvent****"+cargoRoutedEvent.getContent().getBookingId());
        return new AssignTrackingNumberCommand(
                                   cargoRoutedEvent.getContent().getBookingId(),"");
    }
}
