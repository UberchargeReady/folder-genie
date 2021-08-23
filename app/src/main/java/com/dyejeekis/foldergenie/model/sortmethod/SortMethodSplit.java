package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import java.io.File;

public class SortMethodSplit extends SortMethod {
    // TODO: 8/12/2021 add various options (parameters) for split, such as random split, name, size, etc

    public static final int MAX_FILES_PER_DIR = 99999;

    private final int filesPerDir;

    private int currentDirName;

    public SortMethodSplit(int filesPerDir, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (filesPerDir < 1 || filesPerDir > MAX_FILES_PER_DIR)
            throw new IllegalArgumentException("Invalid number of files per directory");
        this.filesPerDir = filesPerDir;
        this.currentDirName = 0;
    }

    @Override
    public File getTargetDir(File file, File parentDir) {
        File targetDir = super.getTargetDir(file, parentDir);
        if (targetDir.exists() && targetDir.listFiles(File::isFile).length >= filesPerDir) {
            currentDirName++;
            return getTargetDir(file, parentDir);
        }
        return targetDir;
    }

    @Override
    public String getDirName(File file) {
        return String.valueOf(MAX_FILES_PER_DIR - currentDirName);
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.SPLIT;
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return  "Move into folders of " + filesPerDir + " files max" + super.toString();
    }
}
