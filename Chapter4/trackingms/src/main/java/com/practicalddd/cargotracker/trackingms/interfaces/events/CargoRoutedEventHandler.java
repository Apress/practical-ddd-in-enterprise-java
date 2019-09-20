package com.practicalddd.cargotracker.trackingms.interfaces.events;

import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import com.practicalddd.cargotracker.trackingms.application.internal.commandservices.AssignTrackingIdCommandService;
import com.practicalddd.cargotracker.trackingms.interfaces.events.transform.TrackingDetailsCommandEventAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class CargoRoutedEventHandler {

    private AssignTrackingIdCommandService assignTrackingIdCommandService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param assignTrackingIdCommandService
     */
    @Inject
    public CargoRoutedEventHandler(AssignTrackingIdCommandService assignTrackingIdCommandService){
        this.assignTrackingIdCommandService = assignTrackingIdCommandService;
    }

    /**
     * Cargo Routed Event Handler Method
     * @param event
     */

    @Transactional
    public void observeCargoRoutedEvent(@Observes CargoRoutedEvent event) {
        System.out.println("****Observing Cargo Routed Event***");
        assignTrackingIdCommandService.assignTrackingNumberToCargo(TrackingDetailsCommandEventAssembler.toCommandFromEvent(event));
    }
}
