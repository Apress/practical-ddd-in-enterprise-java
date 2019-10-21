package com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.assembler;

import com.practicalddd.cargotracker.bookingms.domain.commands.AssignRouteToCargoCommand;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto.Leg;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto.CargoRouteResource;

import java.util.ArrayList;
import java.util.List;

public class AssignRouteCommandDTOAssembler {
    public static AssignRouteToCargoCommand toCommandFromDTO(CargoRouteResource cargoRouteResource){

        List<com.practicalddd.cargotracker.bookingms.domain.model.Leg> legs = new ArrayList<>();

        for(Leg leg:
                cargoRouteResource.getLegs()){
            com.practicalddd.cargotracker.bookingms.domain.model.Leg legDomainModel = new com.practicalddd.cargotracker.bookingms.domain.model.Leg(leg.getVoyageNumber(),
                    leg.getFromUnLocode(),
                    leg.getToUnLocode(),
                    leg.getLoadTime(),
                    leg.getUnloadTime());

            legs.add(legDomainModel);

        }
        return new AssignRouteToCargoCommand(cargoRouteResource.getBookingId(),legs);
    }
}
