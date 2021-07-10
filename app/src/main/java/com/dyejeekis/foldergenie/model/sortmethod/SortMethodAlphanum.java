package com.dyejeekis.foldergenie.model.sortmethod;

import java.io.File;

public class SortMethodAlphanum extends SortMethod {
    public SortMethodAlphanum(boolean addToArchive, boolean addToFilename) {
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
    public File getTargetDir(File file, File parentDir) {
        return null;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.ALPHANUMERIC;
    }
}
