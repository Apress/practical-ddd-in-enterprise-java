package com.practicalddd.cargotracker.bookingms.domain.model;

/**
 * Enum class for the Transport Status of the Cargo
 */
public enum TransportStatus {

    NOT_RECEIVED, IN_PORT, ONBOARD_CARRIER, CLAIMED, UNKNOWN;

    public boolean sameValueAs(TransportStatus other) {
        return this.equals(other);
    }
}
