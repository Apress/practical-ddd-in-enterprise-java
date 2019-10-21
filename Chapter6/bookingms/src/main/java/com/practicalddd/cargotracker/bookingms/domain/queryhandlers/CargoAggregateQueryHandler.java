package com.practicalddd.cargotracker.bookingms.domain.queryhandlers;

import com.practicalddd.cargotracker.bookingms.domain.projections.CargoSummary;
import com.practicalddd.cargotracker.bookingms.domain.queries.CargoSummaryQuery;
import com.practicalddd.cargotracker.bookingms.domain.queries.CargoSummaryResult;
import com.practicalddd.cargotracker.bookingms.domain.queries.ListCargoSummariesQuery;
import com.practicalddd.cargotracker.bookingms.domain.queries.ListCargoSummaryResult;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

/**
 * Class which acts as the Query Handler for all queries related to the Cargo Aggregate Projections
 */
@Component
public class CargoAggregateQueryHandler {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EntityManager entityManager;

    public CargoAggregateQueryHandler(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Query Handler Query which returns the Cargo Summary for a Specific Query
     * @param cargoSummaryQuery
     * @return CargoSummaryResult
     */
    @QueryHandler
    public CargoSummaryResult handle(CargoSummaryQuery cargoSummaryQuery) {
        logger.info("Handling {}", cargoSummaryQuery);

        Query jpaQuery = entityManager.createNamedQuery("CargoSummary.findByBookingId",
                CargoSummary.class).setParameter("bookingId",cargoSummaryQuery.getBookingId());

        CargoSummaryResult result = new CargoSummaryResult((CargoSummary)jpaQuery.getSingleResult());
        logger.info("Returning {}", result);
        return result;
    }

    /**
     * Query Handler for the Query which returns all Cargo summaries
     * @param listCargoSummariesQuery
     * @return CargoSummaryResult
     */
    @QueryHandler
    public ListCargoSummaryResult handle(ListCargoSummariesQuery listCargoSummariesQuery) {
        logger.info("Handling {}", listCargoSummariesQuery);

        Query jpaQuery = entityManager.createNamedQuery("CardSummary.findAll",
                CargoSummary.class);
        jpaQuery.setFirstResult(listCargoSummariesQuery.getOffset());
        jpaQuery.setMaxResults(listCargoSummariesQuery.getLimit());
        ListCargoSummaryResult result = new ListCargoSummaryResult(jpaQuery.getResultList());

        return result;
    }
}
