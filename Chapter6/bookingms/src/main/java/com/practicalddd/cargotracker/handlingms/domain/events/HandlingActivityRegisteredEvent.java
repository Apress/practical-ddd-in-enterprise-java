package com.practicalddd.cargotracker.handlingms.domain.events;

import com.practicalddd.cargotracker.handlingms.domain.model.CargoDetails;
import com.practicalddd.cargotracker.handlingms.domain.model.HandlingEventType;

public class HandlingActivityRegisteredEvent {

    private String activityId;
    private CargoDetails cargoDetails;
    private HandlingEventType handlingEventType;
    public HandlingActivityRegisteredEvent(String activityId,
                                           CargoDetails cargoDetails,
                                           HandlingEventType handlingEventType){
        this.activityId = activityId;
        this.cargoDetails = cargoDetails;
        this.handlingEventType = handlingEventType;
    }

    public String getActivityId(){ return this.activityId;}

    public CargoDetails getCargoDetails(){return this.cargoDetails;}

    public HandlingEventType getHandlingEventType(){return this.handlingEventType;}

    public void setActivityId(String activityId){this.activityId = activityId;}

    public void setCargoDetails(CargoDetails cargoDetails){this.cargoDetails = cargoDetails;}

    public void setHandlingEventType(HandlingEventType handlingEventType){ this.handlingEventType = handlingEventType;}
}
