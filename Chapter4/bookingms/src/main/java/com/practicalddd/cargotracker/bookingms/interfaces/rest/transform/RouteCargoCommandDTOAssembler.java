package com.practicalddd.cargotracker.bookingms.interfaces.rest.transform;

import com.practicalddd.cargotracker.bookingms.domain.model.commands.BookCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.model.commands.RouteCargoCommand;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.dto.BookCargoResource;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.dto.RouteCargoResource;

/**
 * Assembler class to convert the Book Cargo Resource Data to the Book Cargo Model
 */
public class RouteCargoCommandDTOAssembler {

    /**
     * Static method within the Assembler class
     * @param routeCargoResource
     * @return RouteCargoCommand Model
     */
    public static RouteCargoCommand toCommandFromDTO(RouteCargoResource routeCargoResource){

        return new RouteCargoCommand(routeCargoResource.getBookingId());
    }
}
