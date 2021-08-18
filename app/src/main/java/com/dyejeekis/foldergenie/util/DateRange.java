package com.dyejeekis.foldergenie.util;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DateRange implements Serializable {

    private final DateFilter from, to;

    public DateRange(DateFilter from, DateFilter to) {
        if ((from == null && to == null) || (from != null && to != null && from.compareTo(to) > 0))
            throw new IllegalArgumentException("Invalid date range");
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public DateFilter getFrom() {
        return from;
    }

    public DateFilter getTo() {
        return to;
    }

    public boolean overlapsWith(DateRange dateRange) {
        // TODO: 8/14/2021
        return false;
    }
}
