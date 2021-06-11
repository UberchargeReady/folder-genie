package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;
import java.io.Serializable;

public abstract class SortMethod implements Serializable {

    private final boolean useZipArchive;
    private final boolean addToFilename;

    public SortMethod(boolean useZipArchive, boolean addToFilename) {
        this.useZipArchive = useZipArchive;
        this.addToFilename = addToFilename;
    }

    public boolean useZipArchive() {
        return useZipArchive;
    }

    public boolean addToFilename() {
        return addToFilename;
    }

    public abstract String getDirName(File file);

    public abstract File getTargetDir(File file, File parentDir);
}