package com.practicalddd.cargotracker.trackingms.domain.projections;



import javax.persistence.*;

@Entity
public class TrackingProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TrackingNumber trackingNumber; // Aggregate Identifier
    @Embedded
    private BookingId bookingId;

    public TrackingNumber getTrackingNumber(){
        return this.trackingNumber;
    }

    public BookingId getBookingId(){
        return this.bookingId;
    }

    public TrackingProjection(BookingId bookingId, TrackingNumber trackingNumber){
        this.trackingNumber = trackingNumber;
        this.bookingId = bookingId;
    }

}
