package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class DateRange implements Serializable {

    public static final long UNUSED = -1;

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

    public boolean isDefined() {
        return from != null && to != null;
    }

    public long getFromTimestamp() {
        if (from == null) return UNUSED;
        return from.getStartTimestamp();
    }

    public long getToTimestamp() {
        if (to == null) return UNUSED;
        return getTo().getEndTimestamp();
    }

    public int getRangeType() {
        return rangeType;
    }

    public boolean belongsInRange(long t) {
        if (from != null && to != null) return t >= from.getStartTimestamp() && t <= to.getEndTimestamp();
        else if (from == null) return t <= to.getEndTimestamp();
        return t >= from.getStartTimestamp();
    }

    public boolean overlapsWith(@NonNull DateRange range) {
        long from1 = this.getFromTimestamp();
        long from2 = range.getFromTimestamp();
        long to1 = this.getToTimestamp();
        long to2 = range.getToTimestamp();
        if (range.isDefined() && !this.isDefined()) {
            if (to1 == UNUSED) return to2 >= from1;
            else return to1 >= from2;
        } else if (!range.isDefined() && this.isDefined()) {
            if (to2 == UNUSED) return to1 >= from2;
            else return to2 >= from1;
        }
        return (from1 == UNUSED && from2 == UNUSED)
                || (to1 == UNUSED && to2 == UNUSED)
                || (from1 <= to2 && to1 >= from2);
    }

    public static boolean rangesOverlap(@NonNull List<DateRange> ranges) {
        for (int i = 0; i < ranges.size(); i++) {
            for (int j = i+1; j < ranges.size(); j++) {
                if (ranges.get(i).overlapsWith(ranges.get(j))) return true;
            }
        }
        return false;
    }
}
