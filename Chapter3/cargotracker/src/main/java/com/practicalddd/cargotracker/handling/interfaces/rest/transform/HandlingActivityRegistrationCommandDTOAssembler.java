package com.practicalddd.cargotracker.handling.interfaces.rest.transform;


import com.practicalddd.cargotracker.handling.domain.model.commands.HandlingActivityRegistrationCommand;
import com.practicalddd.cargotracker.handling.interfaces.rest.dto.HandlingActivityRegistrationResource;

/**
 * Assembler class to convert the Book Cargo Resource Data to the Book Cargo Model
 */
public class HandlingActivityRegistrationCommandDTOAssembler {

    /**
     * Static method within the Assembler class
     * @param handlingActivityRegistrationResource
     * @return BookCargoCommand Model
     */
    public static HandlingActivityRegistrationCommand toCommandFromDTO(
            HandlingActivityRegistrationResource handlingActivityRegistrationResource){

        return new HandlingActivityRegistrationCommand(
                handlingActivityRegistrationResource.getBookingId(),
                handlingActivityRegistrationResource.getVoyageNumber(),
                handlingActivityRegistrationResource.getUnLocode(),
                handlingActivityRegistrationResource.getHandlingType(),
                java.sql.Date.valueOf(handlingActivityRegistrationResource.getCompletionTime())
        );
    }
}
