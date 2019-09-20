package com.practicalddd.cargotracker.routingms.interfaces.rest;


import com.practicalddd.cargotracker.routingms.application.internal.queryservices.CargoRoutingQueryService;
import com.practicalddd.cargotracker.routingms.domain.model.aggregates.Voyage;
import com.practicalddd.cargotracker.routingms.domain.model.entities.CarrierMovement;
import com.practicalddd.cargotracker.shareddomain.model.TransitEdge;
import com.practicalddd.cargotracker.shareddomain.model.TransitPath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("/cargoRouting")
@ApplicationScoped
public class CargoRoutingController {

    @Inject
    private CargoRoutingQueryService cargoRoutingQueryService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoRoutingQueryService
     */
    @Inject
    public CargoRoutingController(CargoRoutingQueryService cargoRoutingQueryService){
        this.cargoRoutingQueryService = cargoRoutingQueryService;
    }


    /**
     * A highly simplistic implementation of the Routing algorithm.
     * It works only with the specifications given in the test case (CNHKG - USNYC and 2019-09-28).
     * The Domain Model can be changed to implement more sophisticated algorithms
     * @param originUnLocode
     * @param destinationUnLocode
     * @param deadline
     * @return
     */
    @GET
    @Path("/optimalRoute")
    @Produces({"application/json"})
    public TransitPath findOptimalRoute(
             @QueryParam("origin") String originUnLocode,
             @QueryParam("destination") String destinationUnLocode,
             @QueryParam("deadline") String deadline) {

        List<Voyage> voyages = cargoRoutingQueryService.findAll();
        TransitPath transitPath = new TransitPath();
        List<TransitEdge> transitEdges = new ArrayList<>();
        for(Voyage voyage:voyages){

            TransitEdge transitEdge = new TransitEdge();
            transitEdge.setVoyageNumber(voyage.getVoyageNumber().getVoyageNumber());
            CarrierMovement movement =
                    ((List<CarrierMovement>)voyage.getSchedule().getCarrierMovements()).get(0);
            transitEdge.setFromDate(movement.getArrivalDate());
            transitEdge.setToDate(movement.getDepartureDate());
            transitEdge.setFromUnLocode(movement.getArrivalLocation().getUnLocCode());
            transitEdge.setToUnLocode(movement.getDepartureLocation().getUnLocCode());
            transitEdges.add(transitEdge);

        }

        transitPath.setTransitEdges(transitEdges);
        return transitPath;

    }
}
