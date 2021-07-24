package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.Serializable;

public abstract class FileGroup implements Serializable {

    private boolean includeSubdirs; // include files in sub-directories

    public FileGroup(boolean includeSubdirs) {
        this.includeSubdirs = includeSubdirs;
    }

    public boolean includeSubdirs() {
        return includeSubdirs;
    }

    public void setIncludeSubdirs(boolean includeSubdirs) {
        this.includeSubdirs = includeSubdirs;
    }

    public abstract File[] listfiles(File dir);

    public abstract FileGroupType getType();

    @NonNull
    @Override
    public String toString() {
        return includeSubdirs ? " (include subdirectories)" : "";
    }
}
