package com.practicalddd.cargotracker.shareddomain.events;

import com.practicalddd.cargotracker.rabbitmqadaptor.ContainsContent;

/**
 * Event Class for the Cargo Routed Event. Wraps up the Cargo
 */

public class CargoRoutedEvent implements ContainsContent<CargoRoutedEventData> {
    private CargoRoutedEventData cargoRoutedEventData;
    public void setContent(CargoRoutedEventData cargoRoutedEventData) { this.cargoRoutedEventData = cargoRoutedEventData; }
    public CargoRoutedEventData getContent() {
        return cargoRoutedEventData;
    }
}