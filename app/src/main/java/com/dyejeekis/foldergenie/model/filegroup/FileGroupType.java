package com.dyejeekis.foldergenie.model.filegroup;

public enum FileGroupType {
    ALL("all", "All files"),
    DATE("date", "Files in date range"),
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
