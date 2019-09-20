package com.practicalddd.cargotracker.bookingms.infrastructure.repositories.jpa;


import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.Cargo;

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
 * Repository class for the Cargo Aggregate. Deals with all repository operations
 * related to the state of the Cargo
 */
@ApplicationScoped
public class CargoRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            CargoRepository.class.getName());

    @PersistenceContext(unitName = "bookingms")
    private EntityManager entityManager;


    /**
     * Returns the Cargo Aggregate based on the Booking Identifier of a Cargo
     * @param bookingId
     * @return
     */
    public Cargo find(BookingId bookingId) {
        Cargo cargo;
        try {
            cargo = entityManager.createNamedQuery("Cargo.findByBookingId",
                    Cargo.class)
                    .setParameter("bookingId", bookingId)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Find called on non-existant Booking ID.", e);
            cargo = null;
        }

        return cargo;
    }

    /**
     * Stores the Cargo Aggregate
     * @param cargo
     */
    public void store(Cargo cargo) {
        entityManager.persist(cargo);
        entityManager.flush();
    }

    /**
     * Gets next Booking Identifier
     * @return
     */

    public String nextBookingId() {
        String random = UUID.randomUUID().toString().toUpperCase();
        return random.substring(0, random.indexOf("-"));
    }

    /**
     * Find all Cargo Aggregates
     * @return
     */
    public List<Cargo> findAll() {
        return entityManager.createNamedQuery("Cargo.findAll", Cargo.class)
                .getResultList();
    }

    /**
     * Get all Booking Identifiers
     * @return
     */
    public List<BookingId> findAllBookingIds() {
        List<BookingId> bookingIds = new ArrayList<BookingId>();

        try {
            bookingIds = entityManager.createNamedQuery(
                    "Cargo.getAllTrackingIds", BookingId.class).getResultList();
        } catch (NoResultException e) {
            logger.log(Level.FINE, "Unable to get all tracking IDs", e);
        }

        return bookingIds;
    }
}
