package com.practicalddd.cargotracker.bookingms.domain.model.valueobjects;


import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
public class LastCargoHandledEvent {

    private Integer handlingEventId;
    @Transient
    private String handlingEventType;
    @Transient
    private String handlingEventVoyage;
    @Transient
    private String handlingEventLocation;
    // Null object pattern.
    public static final LastCargoHandledEvent EMPTY = new LastCargoHandledEvent();

    public LastCargoHandledEvent(){}


    public LastCargoHandledEvent(Integer handlingEventId, String handlingEventType, String handlignEventVoyage, String handlingEventLocation){
        this.handlingEventId = handlingEventId;
        this.handlingEventType = handlingEventType;
        this.handlingEventVoyage = handlingEventVoyage;
        this.handlingEventLocation = handlingEventLocation;
    }

    public String getHandlingEventType(){return this.handlingEventType;}

    public String getHandlingEventVoyage(){return this.handlingEventVoyage;}

    public Integer getHandlingEventId(){return this.handlingEventId;}

    public void setHandlingEventType(String handlingEventType){this.handlingEventType = handlingEventType;}

    public void setHandlingEventId(Integer handlingEventId){this.handlingEventId = handlingEventId;}

    public void setHandlingEventVoyage(String handlingEventVoyage){this.handlingEventVoyage = handlingEventVoyage;}

    public void setHandlingEventLocation(String handlingEventLocation){this.handlingEventLocation = handlingEventLocation;}

    public String getHandlingEventLocation(){return this.handlingEventLocation;}


}
