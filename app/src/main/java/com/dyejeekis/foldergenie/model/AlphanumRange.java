package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

public class AlphanumRange {

    private final String startStr, endStr;

    public AlphanumRange(String startStr, String endStr) {
        if (startStr.compareTo(endStr) > 0)
            throw new IllegalArgumentException("Invalid alphanum range");
        this.startStr = startStr;
        this.endStr = endStr;
    }

    @NonNull
    @Override
    public String toString() {
        if (startStr.compareTo(endStr) == 0) return "'" + startStr + "'";
        return "'" + startStr + "' to '" + endStr + "'";
    }

    public String getStartStr() {
        return startStr;
    }

    public String getEndStr() {
        return endStr;
    }
}
