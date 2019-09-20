package com.practicalddd.cargotracker.routingms.domain.model.entities;


import com.practicalddd.cargotracker.routingms.domain.model.valueobjects.Location;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="carrier_movement")
public class CarrierMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name="arrival_date")
    private Date arrivalDate;
    @Temporal(TemporalType.DATE)
    @Column(name="departure_Date")
    private Date departureDate;
    @Embedded
    private Location arrivalLocation;
    @Embedded
    @AttributeOverride(name = "unLocCode", column = @Column(name = "departure_location_id"))
    private Location departureLocation;

    public CarrierMovement(){}

    public CarrierMovement(Location departureLocation,
                           Location arrivalLocation, Date departureDate, Date arrivalDate) {

        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }


}
