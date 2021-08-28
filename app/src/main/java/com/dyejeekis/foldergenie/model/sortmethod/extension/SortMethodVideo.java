package com.dyejeekis.foldergenie.model.sortmethod.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupVideo;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.Arrays;

public class SortMethodVideo extends SortMethod {

    public static final String DIR_NAME = "videos";

    private final ExtensionGroup videoExtensions;

    public SortMethodVideo(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        this.videoExtensions = new ExtensionGroup(Arrays.asList(FileGroupVideo.EXTENSIONS));
    }

    @Override
    public String getDirName(File file) {
        return (videoExtensions.contains(GeneralUtil.getFileExtension(file))) ? DIR_NAME : "";
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Move all videos into one folder";
        return s + super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.VIDEO;
    }
}
