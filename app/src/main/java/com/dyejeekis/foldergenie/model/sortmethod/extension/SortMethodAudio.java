package com.dyejeekis.foldergenie.model.sortmethod.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupAudio;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.Arrays;

public class SortMethodAudio extends SortMethod {

    public static final String DIR_NAME = "audio";

    private final ExtensionGroup audioExtensions;

    public SortMethodAudio(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        this.audioExtensions = new ExtensionGroup(Arrays.asList(FileGroupAudio.EXTENSIONS));
    }

    @Override
    public String getDirName(File file) {
        return (audioExtensions.contains(GeneralUtil.getFileExtension(file))) ? DIR_NAME : "";
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Move all audio files into one folder";
        return s + super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.AUDIO;
    }
}
