package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public abstract class SortMethod {

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

    public abstract File getTargetDir(File rootDir, File dir);
}
