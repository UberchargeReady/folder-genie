package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

public class DateRange {

    private final DateFilter from, to;

    public DateRange(@NonNull DateFilter from, @NonNull DateFilter to) {
        this.from = from;
        this.to = to;
    }

    public DateFilter getFrom() {
        return from;
    }

    public DateFilter getTo() {
        return to;
    }
}
