package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodImageRes extends SortMethod {
    public SortMethodImageRes(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
    }

    @Override
    public String getDirName(File file) {
        return null;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.IMAGE_RESOLUTION;
    }
}
