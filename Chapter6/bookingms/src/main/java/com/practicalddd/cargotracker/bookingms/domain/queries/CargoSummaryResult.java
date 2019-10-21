package com.practicalddd.cargotracker.bookingms.domain.queries;

import com.practicalddd.cargotracker.bookingms.domain.projections.CargoSummary;


/**
 * Implementation of the Cargo Summary Result class which contains the results of the execution of the
 * CargoSummaryQuery. The result contains data from the CargoSummary Projection
 */
public class CargoSummaryResult {
    private final CargoSummary cargoSummary;

    public CargoSummaryResult(CargoSummary cargoSummary) { this.cargoSummary = cargoSummary; }

    public CargoSummary getCargoSummary() { return cargoSummary;}
}
