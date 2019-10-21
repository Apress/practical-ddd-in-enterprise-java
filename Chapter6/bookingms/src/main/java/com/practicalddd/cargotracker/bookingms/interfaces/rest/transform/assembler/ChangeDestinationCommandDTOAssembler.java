package com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.assembler;


import com.practicalddd.cargotracker.bookingms.domain.commands.ChangeDestinationCommand;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto.ChangeDestinationResource;

public class ChangeDestinationCommandDTOAssembler {

    public static ChangeDestinationCommand toCommandFromDTO(ChangeDestinationResource changeDestinationResource){
        return new ChangeDestinationCommand(changeDestinationResource.getBookingId(),changeDestinationResource.getNewDestLocation());
    }
}
