package com.dyejeekis.foldergenie.model.filegroup;

public class FileGroupImage extends FileGroupExtension {

    public static String[] IMAGE_EXTENSIONS = {
            "tif", "tiff",
            "bmp",
            "jpg", "jpeg",
            "gif",
            "png",
            "eps",
            "raw", "cr2", "nef", "orf", "sr2"
    };

    public FileGroupImage(boolean includeSubdirs) {
        super(includeSubdirs);
        for (String e : IMAGE_EXTENSIONS) {
            addExtension(e);
        }
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.IMAGE;
    }
}
