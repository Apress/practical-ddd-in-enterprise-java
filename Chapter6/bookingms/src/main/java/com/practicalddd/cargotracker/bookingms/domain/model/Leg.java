package com.practicalddd.cargotracker.bookingms.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Leg of the Itinerary that the Cargo is currently on
 */
public class Leg extends Itinerary {
    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("MM/dd/yyyy hh:mm a z");

    private  String voyageNumber;
    private  String fromUnLocode;
    private  String toUnLocode;
    private  String loadTime;
    private  String unloadTime;

    private Voyage voyage;
    private Location loadLocation;
    private Location unloadLocation;
    private Date loadTimeDate;
    private Date unloadTimeDate;

    public Leg(Voyage voyage, Location loadLocation,
               Location unloadLocation, Date loadTimeDate, Date unloadTimeDate){
        this.voyage = voyage;
        this.loadLocation = loadLocation;
        this.unloadLocation = unloadLocation;
        this.loadTimeDate = loadTimeDate;
        this.unloadTimeDate = unloadTimeDate;

    }

    public Leg(
            String voyageNumber,
            String fromUnLocode,
            String toUnLocode,
            String loadTime,
            String unloadTime) {
        this.voyageNumber = voyageNumber;
        this.fromUnLocode = fromUnLocode;
        this.toUnLocode = toUnLocode;
        this.loadTime = loadTime;
        this.unloadTime = unloadTime;
    }

    public String getVoyageNumber() {
        return voyageNumber;
    }

    public String getFromUnLocode() {
        return fromUnLocode;
    }

    public String getToUnLocode() {
        return toUnLocode;
    }

    public String getLoadTime() {
        return loadTime;
    }

    public String getUnloadTime() {
        return unloadTime;
    }

    public void setVoyageNumber(String voyageNumber) { this.voyageNumber =  voyageNumber; }

    public void setFromUnLocode(String fromUnLocode) { this.fromUnLocode = fromUnLocode; }

    public void setToUnLocode(String toUnLocode) { this.toUnLocode =  toUnLocode; }

    public void setLoadTime(String loadTime) { this.loadTime = loadTime; }

    public void setUnloadTime(String unloadTime) { this.unloadTime = unloadTime; }


    @Override
    public String toString() {
        return "Leg{" + "voyageNumber=" + voyageNumber + ", from=" + fromUnLocode + ", to=" + toUnLocode + ", loadTime=" + loadTime + ", unloadTime=" + unloadTime + '}';
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Location getLoadLocation() {
        return loadLocation;
    }

    public Location getUnloadLocation() {
        return unloadLocation;
    }

    public Date getLoadTimeDate() {
        return loadTimeDate;
    }

    public Date getUnloadTimeDate() {
        return unloadTimeDate;
    }
}
