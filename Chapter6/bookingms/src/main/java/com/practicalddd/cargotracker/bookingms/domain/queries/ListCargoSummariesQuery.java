package com.practicalddd.cargotracker.bookingms.domain.queries;

/**
 * Query to list all the Cargo summaries. This contains offset/limit information which is used for pagination
 */
public class ListCargoSummariesQuery {

    private final int offset;
    private final int limit;

    public ListCargoSummariesQuery(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "Find card summaries query with offset=" + offset + ", limit=" + limit;
    }
}
