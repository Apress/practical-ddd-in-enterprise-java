package com.practicalddd.cargotracker.handlingms.domain.model.valueobjects;


import com.practicalddd.cargotracker.handlingms.domain.model.aggregates.HandlingActivity;

import java.util.*;

public class HandlingActivityHistory {

    private final List<HandlingActivity> handlingEvents;
    // Null object pattern.
    public static final HandlingActivityHistory EMPTY = new HandlingActivityHistory(
            Collections.<HandlingActivity>emptyList());

    public HandlingActivityHistory(Collection<HandlingActivity> handlingEvents) {

        this.handlingEvents = new ArrayList<>(handlingEvents);
    }

    public List<HandlingActivity> getAllHandlingEvents() {
        return handlingEvents;
    }

    /**
     * @return A distinct list (no duplicate registrations) of handling events,
     * ordered by completion time.
     */
    public List<HandlingActivity> getDistinctEventsByCompletionTime() {
        List<HandlingActivity> ordered = new ArrayList<>(new HashSet<>(
                handlingEvents));
        Collections.sort(ordered, BY_COMPLETION_TIME_COMPARATOR);

        return Collections.unmodifiableList(ordered);
    }

    /**
     * @return Most recently completed event, or null if the delivery history is
     * empty.
     */
    public HandlingActivity getMostRecentlyCompletedEvent() {
        List<HandlingActivity> distinctEvents = getDistinctEventsByCompletionTime();

        if (distinctEvents.isEmpty()) {
            return null;
        } else {
            return distinctEvents.get(distinctEvents.size() - 1);
        }
    }

    private boolean sameValueAs(HandlingActivityHistory other) {
        return other != null
                && this.handlingEvents.equals(other.handlingEvents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HandlingActivityHistory other = (HandlingActivityHistory) o;
        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return handlingEvents.hashCode();
    }

    private static final Comparator<HandlingActivity> BY_COMPLETION_TIME_COMPARATOR = new Comparator<HandlingActivity>() {
        @Override
        public int compare(HandlingActivity he1, HandlingActivity he2) {
            return he1.getCompletionTime().compareTo(he2.getCompletionTime());
        }
    };
}
