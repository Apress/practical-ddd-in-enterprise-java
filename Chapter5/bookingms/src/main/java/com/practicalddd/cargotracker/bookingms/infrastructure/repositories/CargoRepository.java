package com.practicalddd.cargotracker.bookingms.infrastructure.repositories;

import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository class for the Cargo Aggregate
 */
public interface CargoRepository extends JpaRepository<Cargo, Long> {

     Cargo findByBookingId(String BookingId);

     List<BookingId> findAllBookingIds();

     List<Cargo> findAll();

}
