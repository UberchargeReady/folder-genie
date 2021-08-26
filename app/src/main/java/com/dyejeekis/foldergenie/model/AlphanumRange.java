package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;
import java.util.List;

public class AlphanumRange implements Serializable {

    private final String from, to;

    public AlphanumRange(String from, String to) {
        if ((from == null && to == null) ||
                (from != null && to != null && from.compareTo(to) > 0))
            throw new IllegalArgumentException("Invalid alphanumeric range");
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public String toString() {
        if (from == null) return "up to " + to + "-";
        if (to == null) return "from " + from +"- onwards";
        if (from.compareTo(to) == 0)
            return "starting with " + from + "-";
        return "from " + from + "- up to " + to + "-";
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isDefined() {
        return from != null && to != null;
    }

    public boolean overlapsWith(@NonNull AlphanumRange range) {
        if (range.isDefined() && !this.isDefined()) {
            if (to == null) return range.getTo().compareTo(from) >= 0;
            else return to.compareTo(range.getFrom()) >= 0;
        } else if (!range.isDefined() && this.isDefined()) {
            if (range.getTo() == null) return to.compareTo(range.getFrom()) >= 0;
            else return range.getTo().compareTo(from) >= 0;
        }
        return (from == null && range.getFrom() == null)
                || (to == null && range.getTo() == null)
                || (this.isDefined() && range.isDefined()
                && from.compareTo(range.getTo()) <= 0 && to.compareTo(range.getFrom()) >= 0);
    }

    public static boolean rangesOverlap(@NonNull List<AlphanumRange> ranges) {
        for (int i = 0; i < ranges.size(); i++) {
            for (int j = i+1; j < ranges.size(); j++) {
                if (ranges.get(i).overlapsWith(ranges.get(j))) return true;
            }
        }
        return false;
    }
}
