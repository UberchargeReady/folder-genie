package com.dyejeekis.foldergenie.model.sortmethod.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupImage;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.Arrays;

public class SortMethodImage extends SortMethod {

    public static final String DIR_NAME = "images";

    private final ExtensionGroup imageExtensions;

    public SortMethodImage(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        this.imageExtensions = new ExtensionGroup(Arrays.asList(FileGroupImage.EXTENSIONS));
    }

    @Override
    public String getDirName(File file) {
        return (imageExtensions.contains(GeneralUtil.getFileExtension(file))) ? DIR_NAME : "";
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Move all images into one folder";
        return s + super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.IMAGE;
    }
}
