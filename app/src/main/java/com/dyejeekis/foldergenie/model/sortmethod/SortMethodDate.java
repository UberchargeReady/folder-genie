package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodDate extends SortMethod {
    public SortMethodDate(boolean useZipArchive, boolean addToFilename) {
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
        // TODO: 6/12/2021
        return null;
    }
}
