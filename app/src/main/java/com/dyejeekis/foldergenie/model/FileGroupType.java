package com.dyejeekis.foldergenie.model;

public enum FileGroupType {
    ALL("All files"),
    DATE_CREATED("Files created at (date)"),
    DATE_MODIFIED("Files modified at (date)"),
    YEAR_CREATED("Files created in (year)"),
    YEAR_MODIFIED("Files modified in (year)"),
    MONTH_CREATED("Files created in (month)"),
    MONTH_MODIFIED("Files modified in (month)"),
    SIZE("Files in size range"),
    FILE_EXTENSION("Files with the extension"),
    IMAGE("All images"),
    VIDEO("All videos"),
    AUDIO("All audio files");

    public final String label;

    FileGroupType(String label) {
        this.label = label;
    }
}
