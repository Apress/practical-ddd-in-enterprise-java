package com.practicalddd.cargotracker.handlingms.domain.model.aggregates;


import com.practicalddd.cargotracker.handlingms.domain.model.valueobjects.CargoBookingId;
import com.practicalddd.cargotracker.handlingms.domain.model.valueobjects.Location;
import com.practicalddd.cargotracker.handlingms.domain.model.valueobjects.Type;
import com.practicalddd.cargotracker.handlingms.domain.model.valueobjects.VoyageNumber;

import javax.persistence.*;
import java.util.Date;

/**
 * Root Aggregate for the Handling Bounded Context
 */
@Entity
@NamedQuery(name = "HandlingEvent.findByBookingId",
        query = "Select e from HandlingActivity e where e.cargoBookingId.bookingId = :bookingId")
@Table(name="handling_activity")
public class HandlingActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name="event_type")
    private Type type;
    @Embedded
    private VoyageNumber voyageNumber;
    @Embedded
    private Location location;
    @Temporal(TemporalType.DATE)
    @Column(name = "event_completion_time")
    private Date completionTime;

    @Embedded
    private CargoBookingId cargoBookingId;

    public HandlingActivity(){}


    /**
     * Constructor for the Handling Activity - With a VoyageNumber
     * @param cargoBookingId
     * @param completionTime
     * @param type
     * @param location
     * @param voyageNumber
     */
    public HandlingActivity(CargoBookingId cargoBookingId, Date completionTime,
                        Type type, Location location, VoyageNumber voyageNumber) {

        if (type.prohibitsVoyage()) {
            throw new IllegalArgumentException(
                    "VoyageNumber is not allowed with event type " + type);
        }

        this.voyageNumber = voyageNumber;
        this.completionTime = (Date) completionTime.clone();
        this.type = type;
        this.location = location;
        this.cargoBookingId = cargoBookingId;
    }

    /**
     * Constructor for the Handling Activity - Without a VoyageNumber
     * @param cargoBookingId
     * @param completionTime
     * @param type
     * @param location
     */
    public HandlingActivity(CargoBookingId cargoBookingId, Date completionTime,
                          Type type, Location location) {

        System.out.println("***Type is**"+type);
        if (type.requiresVoyage()) {
            throw new IllegalArgumentException(
                    "VoyageNumber is required for event type " + type);
        }

        this.completionTime = (Date) completionTime.clone();
        this.type = type;
        this.location = location;
        this.cargoBookingId = cargoBookingId;
        this.voyageNumber = null;
    }


    public Type getType() {
        return this.type;
    }

    public VoyageNumber getVoyage() {
        return this.voyageNumber;
    }

    public Date getCompletionTime() {
        return new Date(this.completionTime.getTime());
    }

    public Location getLocation() { return this.location; }

    public CargoBookingId getCargoBookingId() {
        return this.cargoBookingId;
    }


}
