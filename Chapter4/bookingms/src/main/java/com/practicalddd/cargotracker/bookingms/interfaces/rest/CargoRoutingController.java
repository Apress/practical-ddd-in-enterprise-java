package com.practicalddd.cargotracker.bookingms.interfaces.rest;

import com.practicalddd.cargotracker.bookingms.application.internal.commandservices.CargoBookingCommandService;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.dto.BookCargoResource;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.dto.RouteCargoResource;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.BookCargoCommandDTOAssembler;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.RouteCargoCommandDTOAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cargorouting")
@ApplicationScoped
public class CargoRoutingController {


    private CargoBookingCommandService cargoBookingCommandService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoBookingCommandService
     */
    @Inject
    public CargoRoutingController(CargoBookingCommandService cargoBookingCommandService){
        this.cargoBookingCommandService = cargoBookingCommandService;
    }

    /**
     * POST method to route a cargo
     * @param routeCargoResource
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response routeCargo(RouteCargoResource routeCargoResource){
        System.out.println("***Booking Id is***"+routeCargoResource);
        cargoBookingCommandService.assignRouteToCargo(
                RouteCargoCommandDTOAssembler.toCommandFromDTO(routeCargoResource));

        final Response returnValue = Response.ok()
                .entity("Cargo Routed")
                .build();
        return returnValue;
    }


}
