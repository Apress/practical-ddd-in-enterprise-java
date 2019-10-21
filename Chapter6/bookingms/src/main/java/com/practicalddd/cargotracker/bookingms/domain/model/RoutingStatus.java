package com.practicalddd.cargotracker.bookingms.domain.model;

/**
 * Enum class for the Routing Status of the Cargo
 */
public enum RoutingStatus {

    NOT_ROUTED, ROUTED, MISROUTED;

    public boolean sameValueAs(RoutingStatus other) {
        return this.equals(other);
    }
}