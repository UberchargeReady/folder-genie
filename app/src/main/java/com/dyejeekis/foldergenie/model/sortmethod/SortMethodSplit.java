package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodSplit extends SortMethod {

    private final int filesPerFolder;

    public SortMethodSplit(boolean useZipArchive, boolean addToFilename,
                           int filesPerFolder) {
        super(useZipArchive, addToFilename);
        this.filesPerFolder = filesPerFolder;
    }

    @Override
    public File getTargetDir(File rootDir, File dir) {
        // TODO: 5/31/2021
        return null;
    }
}
