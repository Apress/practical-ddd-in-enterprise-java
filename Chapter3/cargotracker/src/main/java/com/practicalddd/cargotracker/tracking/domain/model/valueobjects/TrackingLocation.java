package com.practicalddd.cargotracker.tracking.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TrackingLocation class represented by a unique 5-diigit UN TrackingLocation code.
 */
@Embeddable
public class TrackingLocation {

    @Column(name = "location_id")
    private String unLocCode;
    public TrackingLocation(){}
    public TrackingLocation(String unLocCode){this.unLocCode = unLocCode;}
    public void setUnLocCode(String unLocCode){this.unLocCode = unLocCode;}
    public String getUnLocCode(){return this.unLocCode;}
}
