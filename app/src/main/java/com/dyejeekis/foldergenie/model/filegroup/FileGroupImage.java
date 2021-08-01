package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

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
    public List<String> getExtensions() {
        return Arrays.asList(IMAGE_EXTENSIONS);
    }

    @NonNull
    @Override
    public String toString() {
        return "All images";
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.IMAGE;
    }
}
