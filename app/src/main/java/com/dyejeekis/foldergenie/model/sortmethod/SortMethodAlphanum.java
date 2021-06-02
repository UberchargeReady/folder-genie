package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodAlphanum extends SortMethod {
    public SortMethodAlphanum(boolean useZipArchive, boolean addToFilename) {
        super(useZipArchive, addToFilename);
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public File getTargetDir(File rootDir, File dir) {
        // TODO: 5/30/2021
        return null;
    }
}
