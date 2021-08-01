package com.dyejeekis.foldergenie.util;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DateRange implements Serializable {

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
