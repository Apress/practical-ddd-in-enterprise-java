package com.practicalddd.cargotracker.bookingms.interfaces.rest;

import com.practicalddd.cargotracker.bookingms.application.internal.commandservices.CargoBookingCommandService;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.dto.BookCargoResource;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.BookCargoCommandDTOAssembler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cargobooking")
@ApplicationScoped
public class CargoBookingController {


    private CargoBookingCommandService cargoBookingCommandService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoBookingCommandService
     */
    @Inject
    public CargoBookingController(CargoBookingCommandService cargoBookingCommandService){
        this.cargoBookingCommandService = cargoBookingCommandService;
    }

    /**
     * POST method to book a cargo
     * @param bookCargoResource
     */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookCargo(BookCargoResource bookCargoResource){
        BookingId bookingId  = cargoBookingCommandService.bookCargo(
                BookCargoCommandDTOAssembler.toCommandFromDTO(bookCargoResource));

        final Response returnValue = Response.ok()
                .entity(bookingId)
                .build();
        return returnValue;
    }


}
