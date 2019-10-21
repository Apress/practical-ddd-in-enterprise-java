package com.practicalddd.cargotracker.trackingms.application.internal.commandgateways;


import com.practicalddd.cargotracker.trackingms.domain.projections.TrackingProjection;
import com.practicalddd.cargotracker.trackingms.infrastructure.repositories.jpa.TrackingRepository;
import org.springframework.stereotype.Service;


/**
 * Application Service class for the Tracking Command Service
 */
@Service
public class TrackingProjectionService {

    private TrackingRepository trackingRepository;

    public TrackingProjectionService(TrackingRepository trackingRepository){
        this.trackingRepository = trackingRepository;

    }

    /**
     * Stores the Cargo Summary Aggregate Projection
     * @param trackingProjection
     */
    public void assignTrackingNumberToCargo(TrackingProjection trackingProjection){
        trackingRepository.save(trackingProjection);
    }

}
