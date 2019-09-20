package com.practicalddd.cargotracker.trackingms.domain.model.aggregates;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrackingNumber {

    @Column(name="tracking_number")
    private String trackingNumber;

    public TrackingNumber(){}

    public TrackingNumber(String trackingNumber){this.trackingNumber = trackingNumber;}

    public String getTrackingNumber(){return this.trackingNumber;}
}
