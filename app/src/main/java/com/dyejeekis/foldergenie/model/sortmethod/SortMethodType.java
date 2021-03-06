package com.dyejeekis.foldergenie.model.sortmethod;

public enum SortMethodType {
    DATE("date", "Sort selected files by date range"),
    YEAR("year", "Sort selected files by year"),
    MONTH("month", "Sort selected files by month"),
    SIZE("size", "Sort selected files by size range"),
    NAME("name", "Sort selected files by alphanumeric range"),
    EXTENSION("extension", "Sort selected files by file extension"),
    AUDIO("audio", "Move all audio files into a single folder"),
    VIDEO("video", "Move all videos into a single folder"),
    IMAGE("image", "Move all images into a single folder"),
    DOCUMENT("document", "Move all documents into a single folder"),
    FILETYPE("filetype", "Sort selected files by file type (audio,video,image,document)"),
    IMAGE_RESOLUTION("imageRes", "Sort selected files by image resolution"),
    SPLIT("split", "Move selected files into folders each containing a maximum number of files"),
    FOLDER("folder", "Move selected files into a single folder");

    public final String name, label;

    SortMethodType(String name, String label) {
        this.name = name;
        this.label = label;
    }

}
