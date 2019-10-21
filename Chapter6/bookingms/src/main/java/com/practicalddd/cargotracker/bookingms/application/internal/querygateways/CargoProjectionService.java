package com.practicalddd.cargotracker.bookingms.application.internal.querygateways;

import com.practicalddd.cargotracker.bookingms.domain.projections.CargoSummary;
import com.practicalddd.cargotracker.bookingms.domain.queries.CargoSummaryQuery;
import com.practicalddd.cargotracker.bookingms.domain.queries.CargoSummaryResult;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Projection Implementation for the Cargo Projection Aggregate
 */
@Service
public class CargoProjectionService {
    private EntityManager entityManager;
    private QueryGateway queryGateway;


    public CargoProjectionService(QueryGateway queryGateway,EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryGateway = queryGateway;
    }



    /**
     * Stores the Cargo Summary Aggregate Projection
     * @param cargoSummary
     */
    public void storeCargoSummary(CargoSummary cargoSummary){
        entityManager.persist(cargoSummary);
    }


    /**
     * Route the CargoSummary Query to the Corresponding Query Handler via the Query Gateway
     * @param bookingId
     * @return
     */
    public CargoSummaryResult queryCargoSummary(String bookingId){
        CargoSummaryQuery cargoSummaryQuery = new CargoSummaryQuery(bookingId);
        CargoSummaryResult cargoSummaryResult = queryGateway.query(cargoSummaryQuery,
                                    CargoSummaryResult.class).join();

        return cargoSummaryResult;

    }


    /**
     * Retrieve the Cargo Summary basis a Booking ID
     * @param bookingId
     * @return
     */
    public CargoSummary getCargoSummary(String bookingId){
        Query jpaQuery = entityManager.createNamedQuery("CargoSummary.findByBookingId",
                CargoSummary.class).setParameter("bookingId",bookingId);

        CargoSummary cargoSummary = (CargoSummary)jpaQuery.getSingleResult();
        return cargoSummary;
    }
}
