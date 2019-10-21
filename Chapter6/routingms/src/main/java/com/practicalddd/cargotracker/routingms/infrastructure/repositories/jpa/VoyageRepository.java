package com.practicalddd.cargotracker.routingms.infrastructure.repositories.jpa;


import com.practicalddd.cargotracker.routingms.domain.model.aggregates.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository class for the Voyage Aggregate.
 */
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    /**
     * Find all Voyage Aggregates
     * @return
     */
    List<Voyage> findAll() ;


}
