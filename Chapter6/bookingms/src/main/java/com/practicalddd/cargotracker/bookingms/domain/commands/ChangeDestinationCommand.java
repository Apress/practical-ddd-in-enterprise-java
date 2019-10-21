package com.practicalddd.cargotracker.bookingms.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ChangeDestinationCommand {
    @TargetAggregateIdentifier
    //Identifier to indicate to Axon framework the unique instance on which the Command needs to be processed
    private String bookingId;

    private String newDestinationLocation;

    public ChangeDestinationCommand(String bookingId,String newDestinationLocation){
        this.bookingId = bookingId;
        this.newDestinationLocation = newDestinationLocation;
    }

    public void setBookingId(String bookingId){
        this.bookingId = bookingId;
    }

    public String getBookingId(){
        return this.bookingId;
    }

    public void setNewDestinationLocation(String newDestinationLocation){ this.newDestinationLocation = newDestinationLocation; }

    public String getNewDestinationLocation(){return this.newDestinationLocation;}

}
