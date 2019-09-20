package com.practicalddd.cargotracker.bookingms.domain.model.valueobjects;

import com.practicalddd.cargotracker.bookingms.domain.model.entities.Location;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A handling activity represents how and where a cargo can be handled, and can
 * be used to express predictions about what is expected to happen to a cargo in
 * the future.
 *
 */
@Embeddable
public class CargoHandlingActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "next_expected_handling_event_type")
    private String type;
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "next_expected_location_id"))
    private Location location;
    @Column(name = "next_expected_voyage_id")
    private Voyage voyage;

    public CargoHandlingActivity() {
    }

    public CargoHandlingActivity(String type, Location location) {
        this.type = type;
        this.location = location;
    }

    public CargoHandlingActivity(String type, Location location,
                                 Voyage voyage) {
        this.type = type;
        this.location = location;
        this.voyage = voyage;
    }

    public String getType() { return type; }

    public Location getLocation() {
        return location;
    }

    public Voyage getVoyage() {
        return voyage;
    }


}
