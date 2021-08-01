package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import java.io.File;

public class SortMethodExtension extends SortMethod {
    public SortMethodExtension(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public String getDirName(File file) {
        // TODO: 8/1/2021
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 8/1/2021
        return super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.FILE_EXTENSION;
    }
}
