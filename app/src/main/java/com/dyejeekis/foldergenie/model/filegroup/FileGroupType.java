package com.dyejeekis.foldergenie.model.filegroup;

public enum FileGroupType {
    ALL("all", "All files"),
    DATE_CREATED("dateCreated", "Files created at (date)"),
    DATE_MODIFIED("dateModified", "Files modified at (date)"),
    YEAR_CREATED("yearCreated", "Files created in (year)"),
    YEAR_MODIFIED("yearModified", "Files modified in (year)"),
    MONTH_CREATED("monthCreated", "Files created in (month)"),
    MONTH_MODIFIED("monthModified", "Files modified in (month)"),
    SIZE("size", "Files in size range"),
    ALPHANUMERIC("alphanum", "Files in alphanumeric range"),
    FILE_EXTENSION("fileExtension", "Files with the extension"),
    IMAGE("images", "All images"),
    VIDEO("videos", "All videos"),
    AUDIO("audio", "All audio files"),
    DOCUMENT("documents", "All documents");

    public final String name, label;

    FileGroupType(String name, String label) {
        this.name = name;
        this.label = label;
    }

}
