package com.practicalddd.cargotracker.bookingms.application.internal.commandservices;

import com.practicalddd.cargotracker.bookingms.application.internal.outboundservices.acl.ExternalCargoRoutingService;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.BookingId;
import com.practicalddd.cargotracker.bookingms.domain.model.aggregates.Cargo;
import com.practicalddd.cargotracker.bookingms.domain.model.commands.BookCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.model.commands.RouteCargoCommand;
import com.practicalddd.cargotracker.bookingms.domain.model.entities.Location;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.CargoItinerary;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.RouteSpecification;
import com.practicalddd.cargotracker.bookingms.infrastructure.repositories.CargoRepository;
import org.springframework.stereotype.Service;


import java.util.UUID;

/**
 * Application Service class for the Cargo Booking Commands
 */

@Service
public class CargoBookingCommandService {


    private CargoRepository cargoRepository;
    private ExternalCargoRoutingService externalCargoRoutingService;

    public CargoBookingCommandService(CargoRepository cargoRepository){

        this.cargoRepository = cargoRepository;
        this.externalCargoRoutingService = externalCargoRoutingService;
    }

    /**
     * Service Command method to book a new Cargo
     * @return BookingId of the Cargo
     */

    public BookingId bookCargo(BookCargoCommand bookCargoCommand){

        String random = UUID.randomUUID().toString().toUpperCase();
        System.out.println("Random is :"+random);
        bookCargoCommand.setBookingId(random.substring(0, random.indexOf("-")));
        Cargo cargo = new Cargo(bookCargoCommand);
        cargoRepository.save(cargo);
        return new BookingId(random);
    }

    /**
     * Service Command method to assign a route to a Cargo
     * @param routeCargoCommand
     */

    public void assignRouteToCargo(RouteCargoCommand routeCargoCommand){

        Cargo cargo = cargoRepository.findByBookingId(routeCargoCommand.getCargoBookingId());
        CargoItinerary cargoItinerary = externalCargoRoutingService.fetchRouteForSpecification(new RouteSpecification(
                new Location(routeCargoCommand.getOriginLocation()),
                new Location(routeCargoCommand.getDestinationLocation()),
                routeCargoCommand.getArrivalDeadline()
        ));
        routeCargoCommand.setCargoItinerary(cargoItinerary);
        cargo.assignToRoute(routeCargoCommand);
        cargoRepository.save(cargo);

    }


}
