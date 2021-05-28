package com.dyejeekis.foldergenie.model;

import java.io.File;

public abstract class SortMethod {

    private final boolean useZipArchive;

    public SortMethod(boolean useZipArchive) {
        this.useZipArchive = useZipArchive;
    }

    public boolean useZipArchive() {
        return useZipArchive;
    }

    public abstract File getTargetDir(File rootDir, File dir);
}
