package com.dyejeekis.foldergenie.model.sortmethod;

public enum SortMethodType {
    DATE_CREATED("Sort by date created"),
    DATE_MODIFIED("Sort by date modified"),
    YEAR_CREATED("Sort by year created"),
    YEAR_MODIFIED("Sort by year modified"),
    MONTH_CREATED("Sort by month created"),
    MONTH_MODIFIED("Sort by month modified"),
    SIZE("Sort by size range"),
    ALPHANUMERIC("Sort alphanumerically"),
    FILE_EXTENSION("Sort by file extension"),
    IMAGE_RESOLUTION("Sort by image resolution");

    public final String label;

    SortMethodType(String label) {
        this.label = label;
    }
}
