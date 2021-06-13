package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodSize extends SortMethod {
    public SortMethodSize(boolean useZipArchive, boolean addToFilename) {
        super(useZipArchive, addToFilename);
    }

    @Override
    public String getDirName(File file) {
        return null;
    }

    @Override
    public File getTargetDir(File file, File parentDir) {
        return null;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.SIZE;
    }
}
