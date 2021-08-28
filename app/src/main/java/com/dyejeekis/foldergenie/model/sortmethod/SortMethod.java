package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.Serializable;

public abstract class SortMethod implements Serializable {

    private final boolean addToArchive;
    private final boolean addToFilename;

    public SortMethod(boolean addToArchive, boolean addToFilename) {
        this.addToArchive = addToArchive;
        this.addToFilename = addToFilename;
    }

    public boolean addToArchive() {
        return addToArchive;
    }

    public boolean addToFilename() {
        return addToFilename;
    }

    public abstract String getDirName(File file);

    public File getTargetDir(File file, File parentDir) {
        return new File(parentDir.getAbsolutePath() + File.separator + getDirName(file));
    }

    public abstract SortMethodType getType();

    @NonNull
    @Override
    public String toString() {
        if (addToArchive() || addToFilename()) {
            String s = " (";
            if (addToArchive()) {
                s = s.concat("use archives"); // TODO: 8/28/2021 change description
                if (addToFilename()) s = s.concat(", ");
            }
            if (addToFilename()) s = s.concat("include in filename"); // TODO: 8/28/2021 change description
            s = s.concat(")");
            return s;
        }
        return "";
    }
}
