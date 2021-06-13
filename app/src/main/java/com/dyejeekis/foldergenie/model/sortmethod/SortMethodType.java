package com.dyejeekis.foldergenie.model.sortmethod;

public enum SortMethodType {
    DATE_CREATED("dateCreated", "Sort by date created"),
    DATE_MODIFIED("dateModified", "Sort by date modified"),
    YEAR_CREATED("yearCreated", "Sort by year created"),
    YEAR_MODIFIED("yearModified", "Sort by year modified"),
    MONTH_CREATED("monthCreated", "Sort by month created"),
    MONTH_MODIFIED("monthModified", "Sort by month modified"),
    SIZE("size", "Sort by size range"),
    ALPHANUMERIC("alphanum", "Sort alphanumerically"),
    FILE_EXTENSION("fileExtension", "Sort by file extension"),
    IMAGE_RESOLUTION("imageRes", "Sort by image resolution"),
    SPLIT("split", "Sort into folders each containing the same number of files");

    public final String name, label;

    SortMethodType(String name, String label) {
        this.name = name;
        this.label = label;
    }

}
