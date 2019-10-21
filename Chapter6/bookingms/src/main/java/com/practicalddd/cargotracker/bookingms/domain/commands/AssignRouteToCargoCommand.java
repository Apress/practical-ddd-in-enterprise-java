package com.practicalddd.cargotracker.bookingms.domain.commands;

import com.practicalddd.cargotracker.bookingms.domain.model.Leg;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

/**
 * Implementation class for the Assign Route to Cargo Command
 */
public class AssignRouteToCargoCommand {
    @TargetAggregateIdentifier
    //Identifier to indicate to Axon framework the unique instance on which the Command needs to be processed
    private String bookingId;
    private List<Leg> legs;
    public AssignRouteToCargoCommand(String bookingId,List<Leg> legs){
        this.bookingId = bookingId;
        this.legs = legs;
    }

    public void setBookingId(String bookingId){this.bookingId = bookingId;}
    public String getBookingId(){return this.bookingId;}
    public void setLegs(List<Leg> legs){this.legs = legs;}
    public List<Leg> getLegs(){return this.legs;}
}
