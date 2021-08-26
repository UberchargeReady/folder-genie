package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;

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
        super(new ExtensionGroup(Arrays.asList(IMAGE_EXTENSIONS)), includeSubdirs);
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
