package com.practicalddd.cargotracker.handling.application.internal.queryservices;

import com.practicalddd.cargotracker.handling.domain.model.valueobjects.HandlingActivityHistory;
import com.practicalddd.cargotracker.handling.infrastructure.repositories.jpa.HandlingActivityRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Application Service which caters to all queries related to the Handling Activity Aggregate
 */
@ApplicationScoped
public class HandlingHistoryService {

    @Inject
    private HandlingActivityRepository handlingActivityRepository;

    @Transactional
    public HandlingActivityHistory getHandlingActivityHistory(String bookingId){
        return handlingActivityRepository.lookupHandlingHistoryOfCargo(bookingId);
    }
}
