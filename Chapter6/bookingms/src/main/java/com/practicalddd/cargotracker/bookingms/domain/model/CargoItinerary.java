package com.practicalddd.cargotracker.bookingms.domain.model;



import java.util.Collections;
import java.util.List;

public class CargoItinerary {

    public static final CargoItinerary EMPTY_ITINERARY = new CargoItinerary();

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
