package com.practicalddd.cargotracker.bookingms.domain.model.valueobjects;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LastCargoHandledEvent {

    @Column(name="last_handling_event_id")
    private Integer handlingEventId;
    @Column(name="last_handling_event_type")
    private String handlingEventType;
    @Column(name="last_handling_event_voyage")
    private String handlingEventVoyage;
    @Column(name="last_handling_event_location")
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
