package com.practicalddd.cargotracker.trackingms.infrastructure.repositories.jpa;


import com.practicalddd.cargotracker.trackingms.domain.model.aggregates.TrackingActivity;
import com.practicalddd.cargotracker.trackingms.domain.model.aggregates.TrackingNumber;
import com.practicalddd.cargotracker.trackingms.domain.model.entities.BookingId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repository class for the Tracking Aggregate. Deals with all repository operations
 * related to the state of the Tracking of the Cargo
 */
@ApplicationScoped
public class TrackingRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            TrackingRepository.class.getName());

    @PersistenceContext(unitName = "trackingms")
    private EntityManager entityManager;


    /**
     * Returns the Cargo Aggregate based on the Tracking Number of the Cargo
     * @param trackingNumber
     * @return TrackingActivity
     */
    public TrackingActivity find(TrackingNumber trackingNumber) {
        TrackingActivity trackingActivity;
        try {
            trackingActivity = entityManager.createNamedQuery("TrackingActivity.findByTrackingNumber",
                    TrackingActivity.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Tracking No.", e);
            trackingActivity = null;
        }

        return trackingActivity;
    }

    /**
     * Returns the Tracking Aggregate based on the Booking Identifier
     * @param bookingId
     * @return
     */
    public TrackingActivity find(BookingId bookingId) {
        TrackingActivity trackingActivity;
        try {
            trackingActivity = entityManager.createNamedQuery("TrackingActivity.findByBookingNumber",
                    TrackingActivity.class)
                    .setParameter("bookingId", bookingId)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Booking ID.", e);
            trackingActivity = null;
        }
        return trackingActivity;
    }

    /**
     * Stores the Tracking Activity Aggregate
     * @param trackingActivity
     */
    public void store(TrackingActivity trackingActivity) {
        entityManager.persist(trackingActivity);
        entityManager.flush();
    }

    /**
     * Gets next Tracking Identifier
     * @return
     */

    public String nextTrackingNumber() {
        String random = UUID.randomUUID().toString().toUpperCase();
        return random.substring(0, random.indexOf("-"));
    }

    /**
     * Find all Tracking Activity Aggregates
     * @return
     */
    public List<TrackingActivity> findAll() {
        return entityManager.createNamedQuery("TrackingActivity.findAll", TrackingActivity.class)
                .getResultList();
    }

    /**
     * Get all Tracking Numbers
     * @return
     */
    public List<TrackingNumber> findAllTrackingNumbers() {
        List<TrackingNumber> trackingNumbers = new ArrayList<TrackingNumber>();

        try {
            trackingNumbers = entityManager.createNamedQuery(
                    "TrackingActivity.", TrackingNumber.class).getResultList();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Unable to get all tracking IDs", e);
        }

        return trackingNumbers;
    }
}
