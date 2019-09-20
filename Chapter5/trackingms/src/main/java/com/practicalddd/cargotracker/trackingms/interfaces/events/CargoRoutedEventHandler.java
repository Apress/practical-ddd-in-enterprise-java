package com.practicalddd.cargotracker.trackingms.interfaces.events;


import com.practicalddd.cargotracker.shareddomain.events.CargoRoutedEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * Event Handler for the Cargo Routed Event that the Tracking Bounded Context is interested in
 */
@Service
@EnableBinding(Sink.class)
public class CargoRoutedEventHandler {

    @StreamListener(target = Sink.INPUT)
    public void receiveEvent(CargoRoutedEvent cargoRoutedEvent) {
        //Process the Event
    }
}
