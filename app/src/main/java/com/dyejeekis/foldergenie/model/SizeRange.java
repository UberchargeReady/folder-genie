package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;

public class SizeRange implements Serializable {

    private final long minSize, maxSize;

    public SizeRange(long minSize, long maxSize) {
        if ((minSize <= 0 && maxSize <= 0) || (minSize > maxSize && maxSize >= 0))
            throw new IllegalArgumentException("Invalid size range");
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @NonNull
    @Override
    public String toString() {
        String minS = GeneralUtil.getReadableFilesize(minSize);
        String maxS = GeneralUtil.getReadableFilesize(maxSize);
        if (minSize == maxSize) return minS;
        if (minSize <= 0) return maxS + " or less";
        if (maxSize <= 0) return minS + " or more";
        return minS + " to " + maxS;
    }

    public long getMinSize() {
        return minSize;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public boolean overlapsWith(SizeRange sizeRange) {
        // TODO: 8/9/2021
        return false;
    }
}
