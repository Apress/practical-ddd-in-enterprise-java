package com.practicalddd.cargotracker.handlingms.interfaces.rest;

import com.practicalddd.cargotracker.handlingms.application.internal.commandservices.HandlingActivityRegistrationCommandService;
import com.practicalddd.cargotracker.handlingms.domain.model.aggregates.HandlingActivity;
import com.practicalddd.cargotracker.handlingms.interfaces.rest.dto.HandlingActivityRegistrationResource;
import com.practicalddd.cargotracker.handlingms.interfaces.rest.transform.HandlingActivityRegistrationCommandDTOAssembler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for the REST API
 */
@Controller    // This means that this class is a Controller
@RequestMapping("/cargohandling")
public  class CargoHandlingController {


    private HandlingActivityRegistrationCommandService handlingActivityRegistrationCommandService; // Application Service Dependency

    /**
     * Provide the dependencies
     * @param handlingActivityRegistrationCommandService
     */
    public CargoHandlingController(HandlingActivityRegistrationCommandService handlingActivityRegistrationCommandService){
        this.handlingActivityRegistrationCommandService = handlingActivityRegistrationCommandService;
    }

    /**
     * POST method to register Handling Activities
     * @param handlingActivityRegistrationResource
     */
    @PostMapping
    @ResponseBody
    public String registerHandlingActivity(@RequestBody HandlingActivityRegistrationResource handlingActivityRegistrationResource){
        System.out.println("***"+handlingActivityRegistrationResource.getBookingId());
        System.out.println("***"+handlingActivityRegistrationResource.getHandlingType());

        handlingActivityRegistrationCommandService.registerHandlingActivityService(HandlingActivityRegistrationCommandDTOAssembler.toCommandFromDTO(handlingActivityRegistrationResource));
        return "Handling Activity Registered";
    }
}
