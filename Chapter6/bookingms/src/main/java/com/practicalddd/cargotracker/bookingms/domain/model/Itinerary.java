package com.practicalddd.cargotracker.bookingms.domain.model;

import java.util.Collections;
import java.util.List;

/**
 * Itinerary assigned to the Cargo. Consists of a set of Legs that the Cargo will go through as part of its journey
 */
public class Itinerary extends Cargo {

    private List<Leg> legs = Collections.emptyList();

    public Itinerary() {
        // Nothing to initialize.
    }

    public Itinerary(List<Leg> legs) {
        this.legs = legs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(legs);
    }
}
