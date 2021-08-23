package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DateRange implements Serializable {

    public static final int RANGE_TYPE_CREATED = 1;
    public static final int RANGE_TYPE_MODIFIED = 2;

    private final DateFilter from, to;
    private final int rangeType = RANGE_TYPE_MODIFIED;

    public DateRange(DateFilter from, DateFilter to) {
        if ((from == null && to == null) || (from != null && to != null &&
                from.getStartTimestamp() > to.getEndTimestamp()))
            throw new IllegalArgumentException("Invalid date range");
        if (rangeType != RANGE_TYPE_CREATED && rangeType != RANGE_TYPE_MODIFIED)
            throw new IllegalArgumentException("Invalid range type");
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public String toString() {
        //String s = rangeType == RANGE_TYPE_CREATED ? "created " : "modified ";
        if (from == null) return "up until " + to.toString();
        if (to == null) return "from " + from.toString() + " onwards";
        if (from.equals(to)) return from.toString();
        return from.toString() + " to " + to.toString();
    }

    public DateFilter getFrom() {
        return from;
    }

    public DateFilter getTo() {
        return to;
    }

    public int getRangeType() {
        return rangeType;
    }

    public boolean belongsInRange(long t) {
        if (from != null && to != null) return t >= from.getStartTimestamp() && t <= to.getEndTimestamp();
        else if (from == null) return t <= to.getEndTimestamp();
        return t >= from.getStartTimestamp();
    }

    public boolean overlapsWith(DateRange dateRange) {
        // TODO: 8/14/2021
        return false;
    }
}
