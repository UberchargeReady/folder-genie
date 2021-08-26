package com.dyejeekis.foldergenie.model.filegroup.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import java.util.Arrays;

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
