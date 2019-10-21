package com.practicalddd.cargotracker.tracking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TrackingVoyageNumber {
    @Column(name="voyage_number")
    private String voyageNumber;
    public TrackingVoyageNumber(){}
    public TrackingVoyageNumber(String voyageNumber){this.voyageNumber = voyageNumber;}
    public String getVoyageNumber(){return this.voyageNumber;}
    public void setVoyageNumber(String voyageNumber){this.voyageNumber = voyageNumber;}
}
