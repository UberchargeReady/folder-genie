package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;
import java.util.List;

public class SizeRange implements Serializable {

    public static final long UNUSED = -1;

    private final long min, max;

    public SizeRange(long min, long max) {
        if ((min <= UNUSED && max <= UNUSED) || (min > max && max >= 0))
            throw new IllegalArgumentException("Invalid size range");
        this.min = min;
        this.max = max;
    }

    @NonNull
    @Override
    public String toString() {
        String minS = GeneralUtil.getReadableFilesize(min);
        String maxS = GeneralUtil.getReadableFilesize(max);
        if (min == max) return minS;
        if (min <= UNUSED) return maxS + " or less";
        if (max <= UNUSED) return minS + " or more";
        return minS + " to " + maxS;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public boolean isDefined() {
        return min > UNUSED && max > UNUSED;
    }

    public boolean overlapsWith(@NonNull SizeRange range) {
        if (range.isDefined() && !this.isDefined()) {
            if (max <= UNUSED) return range.getMax() >= min;
            else return max >= range.getMin();
        } else if (!range.isDefined() && this.isDefined()) {
            if (range.getMax() <= UNUSED) return max >= range.getMin();
            else return range.getMax() >= min;
        }
        return (min <= UNUSED && range.getMin() <= UNUSED)
                || (max <= UNUSED && range.getMax() <= UNUSED)
                || (min <= range.getMax() && max >= range.getMin());
    }

    public static boolean rangesOverlap(@NonNull List<SizeRange> ranges) {
        for (int i = 0; i < ranges.size(); i++) {
            for (int j = i+1; j < ranges.size(); j++) {
                if (ranges.get(i).overlapsWith(ranges.get(j))) return true;
            }
        }
        return false;
    }
}
