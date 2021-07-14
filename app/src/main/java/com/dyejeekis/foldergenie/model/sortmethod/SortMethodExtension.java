package com.dyejeekis.foldergenie.model.sortmethod;

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
        return null;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.FILE_EXTENSION;
    }
}
