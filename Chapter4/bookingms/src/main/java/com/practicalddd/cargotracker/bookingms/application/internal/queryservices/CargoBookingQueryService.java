package com.practicalddd.cargotracker.bookingms.application.internal.queryservices;


import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.Cargo;
import com.practicalddd.cargotracker.bookingms.infrastructure.repositories.jpa.CargoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Application Service which caters to all queries related to the Booking Bounded Context
 */
@ApplicationScoped
public class CargoBookingQueryService {


    @Inject
    private CargoRepository cargoRepository; // Inject Dependencies

    /**
     * Find all Cargos
     * @return List<Cargo>
     */
    @Transactional
    public List<Cargo> findAll(){
        return cargoRepository.findAll();
    }

    /**
     * List All Booking Identifiers
     * @return List<BookingId>
     */
   public List<BookingId> getAllBookingIds(){
       return cargoRepository.findAllBookingIds();
   }

    /**
     * Find a specific Cargo based on its Booking Id
     * @param bookingId
     * @return Cargo
     */
    public Cargo find(String bookingId){
        return cargoRepository.find(new BookingId(bookingId));
    }
}
