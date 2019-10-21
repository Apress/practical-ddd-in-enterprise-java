package com.practicalddd.cargotracker.routing.application.internal.queryservices;


import com.practicalddd.cargotracker.routing.domain.model.aggregates.Voyage;
import com.practicalddd.cargotracker.routing.infrastructure.repositories.jpa.VoyageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Application Service class for the Cargo Routing Query service
 */
@ApplicationScoped
public class CargoRoutingQueryService {

    @Inject
    private VoyageRepository voyageRepository; // Inject Dependencies

    /**
     * Returns all Voyages
     * @return
     */
    @Transactional
    public List<Voyage> findAll(){
        return voyageRepository.findAll();
    }


}
