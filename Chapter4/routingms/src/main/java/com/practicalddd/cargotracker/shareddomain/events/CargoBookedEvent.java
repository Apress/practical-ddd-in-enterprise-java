package com.practicalddd.cargotracker.shareddomain.events;

import com.practicalddd.cargotracker.rabbitmqadaptor.ContainsId;

/**
 * Event Class for the Cargo Booked Event. Wraps up the Cargo Booking identifier
 * for the event
 */

public class CargoBookedEvent implements ContainsId<String> {
    private String id;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
