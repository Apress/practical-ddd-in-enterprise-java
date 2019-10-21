package com.practicalddd.cargotracker.tracking.application.internal.commandservices;


import com.practicalddd.cargotracker.tracking.domain.model.aggregates.TrackingActivity;
import com.practicalddd.cargotracker.tracking.domain.model.aggregates.TrackingNumber;
import com.practicalddd.cargotracker.tracking.domain.model.commands.AddTrackingEventCommand;
import com.practicalddd.cargotracker.tracking.domain.model.commands.AssignTrackingNumberCommand;
import com.practicalddd.cargotracker.tracking.domain.model.entities.TrackingBookingId;
import com.practicalddd.cargotracker.tracking.infrastructure.repositories.jpa.TrackingRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


/**
 * Application Service class for the Tracking Command Service
 */
@ApplicationScoped
public class AssignTrackingIdCommandService {

    @Inject
    private TrackingRepository trackingRepository;

    /**
     * Service Command method to assign a tracking id to the booked cargo
     * @return Tracking Number of the Cargo
     */
    @Transactional // Inititate the Transaction
    public TrackingNumber assignTrackingNumberToCargo(AssignTrackingNumberCommand assignTrackingNumberCommand){
        String trackingNumber = trackingRepository.nextTrackingNumber();
        assignTrackingNumberCommand.setTrackingNumber(trackingNumber);
        TrackingActivity activity = new TrackingActivity(assignTrackingNumberCommand);
        System.out.println("***Going to store in repository");
        trackingRepository.store(activity); //Store the Cargo

        return new TrackingNumber(trackingNumber);
    }

    /**
     * Service Command method to assign a route to a Cargo
     * @param addTrackingEventCommand
     */
    @Transactional
    public void addTrackingEvent(AddTrackingEventCommand addTrackingEventCommand){
        TrackingActivity trackingActivity = trackingRepository.findByBookingId(
                new TrackingBookingId(addTrackingEventCommand.getBookingId()));

        trackingActivity.addTrackingEvent(addTrackingEventCommand);
        trackingRepository.store(trackingActivity);
    }


}
