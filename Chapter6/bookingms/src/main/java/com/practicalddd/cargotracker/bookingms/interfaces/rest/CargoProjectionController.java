package com.practicalddd.cargotracker.bookingms.interfaces.rest;


import com.practicalddd.cargotracker.bookingms.application.internal.querygateways.CargoProjectionService;
import com.practicalddd.cargotracker.bookingms.domain.queries.CargoSummaryResult;
import org.springframework.web.bind.annotation.*;

/**
 * REST API for all operations related to the Cargo Aggregate Projections
 */
@RestController
@RequestMapping("/cargosummary")
public class CargoProjectionController {


    private CargoProjectionService cargoProjectionService;
    public CargoProjectionController(CargoProjectionService cargoProjectionService){
        this.cargoProjectionService = cargoProjectionService;
    }

    @GetMapping("/{bookingid}")
    public CargoSummaryResult cargoSummary(@PathVariable(value = "bookingid") String bookingId){
       // commandGateway.send(BookCargoCommandDTOAssembler.toCommandFromDTO(bookCargoCommandResource));
        CargoSummaryResult cargoSummary = cargoProjectionService.queryCargoSummary(bookingId);
        return cargoSummary;
    }



}
