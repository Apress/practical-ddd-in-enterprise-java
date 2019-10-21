package com.practicalddd.cargotracker.bookingms.interfaces.rest;


import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.assembler.BookCargoCommandDTOAssembler;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto.BookCargoResource;
import com.practicalddd.cargotracker.bookingms.application.internal.commandgateways.CargoBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST API for the Book Cargo Command
 */
@RestController
@RequestMapping("/cargobooking")
public class CargoBookingController {


    private final CargoBookingService cargoBookingService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoBookingService
     */
    public CargoBookingController(CargoBookingService cargoBookingService){
        this.cargoBookingService = cargoBookingService;
    }

    /**
     * POST method to book a cargo
     * @param bookCargoCommandResource
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void bookCargo(@RequestBody final BookCargoResource bookCargoCommandResource){
        String random = UUID.randomUUID().toString().toUpperCase();
        bookCargoCommandResource.setBookingId(random.substring(0, random.indexOf("-")));
        cargoBookingService.bookCargo(BookCargoCommandDTOAssembler.toCommandFromDTO(bookCargoCommandResource));
    }
}