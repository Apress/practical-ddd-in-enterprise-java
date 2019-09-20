package com.practicalddd.cargotracker.routingms.interfaces.rest;

import com.practicalddd.cargotracker.TransitPath;
import com.practicalddd.cargotracker.routingms.application.internal.CargoRoutingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller    // This means that this class is a Controller
@RequestMapping("/cargorouting")
public class CargoRoutingController {


    private CargoRoutingService cargoRoutingService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param cargoRoutingService
     */
    public CargoRoutingController(CargoRoutingService cargoRoutingService){
        this.cargoRoutingService = cargoRoutingService;
    }


    /**
     *
     * @param originUnLocode
     * @param destinationUnLocode
     * @param deadline
     * @return TransitPath - The optimal route for a Route Specification
     */

    @GetMapping(path = "/optimalRoute")
    @ResponseBody
    public TransitPath findOptimalRoute(
             @PathVariable("origin") String originUnLocode,
             @PathVariable("destination") String destinationUnLocode,
             @PathVariable("deadline") String deadline) {

        TransitPath transitPath = cargoRoutingService.findOptimalRoute(originUnLocode,destinationUnLocode,deadline);

        return transitPath;

    }
}
