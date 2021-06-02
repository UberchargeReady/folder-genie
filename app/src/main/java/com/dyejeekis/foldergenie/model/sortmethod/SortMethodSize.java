package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodSize extends SortMethod {
    public SortMethodSize(boolean useZipArchive, boolean addToFilename) {
        super(useZipArchive, addToFilename);
    }

    @Override
    public File getTargetDir(File rootDir, File dir) {
        // TODO: 5/30/2021
        return null;
    }
}
