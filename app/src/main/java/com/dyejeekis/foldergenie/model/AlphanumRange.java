package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class AlphanumRange implements Serializable {

    private final String startStr, endStr;

    public AlphanumRange(String startStr, String endStr) {
        if ((startStr == null && endStr == null) ||
                (startStr != null && endStr != null && startStr.compareTo(endStr) > 0))
            throw new IllegalArgumentException("Invalid alphanumeric range");
        this.startStr = startStr;
        this.endStr = endStr;
    }

    @NonNull
    @Override
    public String toString() {
        if (startStr == null) return "up to " + endStr + "-";
        if (endStr == null) return "from " + startStr +"- onwards";
        if (startStr.compareTo(endStr) == 0)
            return "starting with " + startStr + "-";
        return "from " + startStr + "- up to " + endStr + "-";
    }

    public String getStartStr() {
        return startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public boolean overlapsWith(AlphanumRange alphanumRange) {
        // TODO: 8/9/2021
        return false;
    }
}
