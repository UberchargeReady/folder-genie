package com.dyejeekis.foldergenie.model.filegroup;

import java.io.File;

public abstract class FileGroup {

    private final boolean includeSubdirs; // include files in sub-directories

    public FileGroup(boolean includeSubdirs) {
        this.includeSubdirs = includeSubdirs;
    }

    public boolean includeSubdirs() {
        return includeSubdirs;
    }

    public abstract File[] listfiles(File dir);
}
