package com.dyejeekis.foldergenie.model.sortmethod;

public enum SortMethodType {
    DATE("date", "Sort by date range"),
    SIZE("size", "Sort by size range"),
    ALPHANUMERIC("alphanum", "Sort by alphanumeric range"),
    FILE_EXTENSION("fileExtension", "Sort by file extension"),
    IMAGE_RESOLUTION("imageRes", "Sort by image resolution"),
    SPLIT("split", "Sort into folders each containing the same number of files");

    public final String name, label;

    SortMethodType(String name, String label) {
        this.name = name;
        this.label = label;
    }

}
