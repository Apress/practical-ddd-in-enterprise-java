package com.practicalddd.cargotracker.bookingms.domain.model.valueobjects;


import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Embeddable
public class CargoItinerary {

    public static final CargoItinerary EMPTY_ITINERARY = new CargoItinerary();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cargo_id")
    private List<Leg> legs = Collections.emptyList();

    public CargoItinerary() {
        // Nothing to initialize.
    }

    public CargoItinerary(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }
}
