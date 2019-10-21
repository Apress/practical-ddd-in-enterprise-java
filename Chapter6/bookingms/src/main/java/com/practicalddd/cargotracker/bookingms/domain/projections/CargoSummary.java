package com.practicalddd.cargotracker.bookingms.domain.projections;

import com.practicalddd.cargotracker.bookingms.domain.model.RoutingStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Projection class for the Cargo Aggregate implemented as a regular JPA Entity. Contains a summary of the Cargo Aggregate
 */
@Entity
@Table(name="cargo_summary_projection")
@NamedQueries({ //Named Queries
        @NamedQuery(name = "CargoSummary.findAll",
                query = "Select c from CargoSummary c"),
        @NamedQuery(name = "CargoSummary.findByBookingId",
                query = "Select c from CargoSummary c where c.booking_id = :bookingId"),
        @NamedQuery(name = "Cargo.getAllBookingIds",
                query = "Select c.booking_id from CargoSummary c") })
public class CargoSummary {

    @Id
    private String booking_id;
    @Column
    private String transport_status;
    @Column
    @Enumerated(EnumType.STRING)
    private RoutingStatus routing_status;
    @Column
    private String spec_origin_id;
    @Column
    private String spec_destination_id;
    @Temporal(TemporalType.DATE)
    private Date deadline;

    protected CargoSummary(){
        this.setBooking_id(null);
    }

    public CargoSummary(String booking_id,
                        String transport_status,
                        RoutingStatus routing_status,
                        String spec_origin_id,
                        String spec_destination_id,
                        Date deadline){
        this.setBooking_id(booking_id);
        this.setTransport_status(transport_status);
        this.setRouting_status(routing_status);
        this.setSpec_origin_id(spec_origin_id);
        this.setSpec_destination_id(spec_destination_id);
        this.setDeadline(new Date());
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getTransport_status() {
        return transport_status;
    }

    public void setTransport_status(String transport_status) {
        this.transport_status = transport_status;
    }

    public RoutingStatus getRouting_status() {
        return routing_status;
    }

    public void setRouting_status(RoutingStatus routing_status) {
        this.routing_status = routing_status;
    }

    public String getSpec_origin_id() {
        return spec_origin_id;
    }

    public void setSpec_origin_id(String spec_origin_id) {
        this.spec_origin_id = spec_origin_id;
    }

    public String getSpec_destination_id() {
        return spec_destination_id;
    }

    public void setSpec_destination_id(String spec_destination_id) {
        this.spec_destination_id = spec_destination_id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
