package com.practicalddd.cargotracker.trackingms.domain.model;

import com.practicalddd.cargotracker.trackingms.domain.commands.AssignTrackingDetailsToCargoCommand;
import com.practicalddd.cargotracker.trackingms.domain.events.CargoTrackedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;


import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
public class TrackingActivity {

    @TargetAggregateIdentifier
    private String trackingId; // Aggregate Identifier


    protected TrackingActivity(){}

    @CommandHandler
    public TrackingActivity(AssignTrackingDetailsToCargoCommand assignTrackingDetailsToCargoCommand) {

        apply(new CargoTrackedEvent(
                    assignTrackingDetailsToCargoCommand.getBookingId(),
                    assignTrackingDetailsToCargoCommand.getTrackingId()
                ));


    }


}
