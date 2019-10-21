package com.practicalddd.cargotracker.trackingms.infrastructure.repositories.jpa;


import com.practicalddd.cargotracker.trackingms.domain.projections.TrackingNumber;
import com.practicalddd.cargotracker.trackingms.domain.projections.TrackingProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


/**
 * Repository class for the Tracking Projection. Deals with all repository operations
 * related to the state of the Tracking of the Cargo
 */
public interface TrackingRepository extends JpaRepository<TrackingProjection,Long> {
    /**
     * Returns the Cargo Aggregate based on the Tracking Number of the Cargo
     * @param trackingNumber
     * @return TrackingActivity
     */
    public TrackingProjection findByTrackingNumber(TrackingNumber trackingNumber);

    /**
     * Find all Tracking Activity Aggregates
     * @return
     */
    public List<TrackingProjection> findAll();



}
