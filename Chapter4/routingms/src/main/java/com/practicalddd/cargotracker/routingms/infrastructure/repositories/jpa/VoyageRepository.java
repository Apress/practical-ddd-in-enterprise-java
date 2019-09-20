package com.practicalddd.cargotracker.routingms.infrastructure.repositories.jpa;


import com.practicalddd.cargotracker.routingms.domain.model.aggregates.Voyage;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.logging.Logger;

/**
 * Repository class for the Voyage Aggregate.
 */
@ApplicationScoped
public class VoyageRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(
            VoyageRepository.class.getName());

    @PersistenceContext(unitName = "routingms")
    private EntityManager entityManager;


    /**
     * Stores the Voyage Aggregate
     * @param voyage
     */
    public void store(Voyage voyage) {
        entityManager.persist(voyage);
        entityManager.flush();
    }

    /**
     * Find all Voyage Aggregates
     * @return
     */
    public List<Voyage> findAll() {
        return entityManager.createNamedQuery("Voyage.findAll", Voyage.class)
                .getResultList();
    }


}
