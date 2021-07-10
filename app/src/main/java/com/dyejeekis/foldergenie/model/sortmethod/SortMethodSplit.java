package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import java.io.File;

public class SortMethodSplit extends SortMethod {

    public static final int MAX_FILES_PER_DIR = 99999;

    // TODO: 6/12/2021 maybe add file size as an option for split
    private final int filesPerDir;

    private int fileCounter, currentDirName;

    public SortMethodSplit(int filesPerDir, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (filesPerDir < 1 || filesPerDir > MAX_FILES_PER_DIR)
            throw new IllegalArgumentException("Invalid number of files per directory");
        this.filesPerDir = filesPerDir;
        this.fileCounter = 0;
        this.currentDirName = 1;
    }

    @Override
    public String getDirName(File file) {
        fileCounter++;
        if (fileCounter > filesPerDir) currentDirName++;
        return String.valueOf(currentDirName);
    }

    @Override
    public File getTargetDir(File file, File parentDir) {
        return new File(parentDir.getAbsolutePath() + File.separator + getDirName(file));
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
        return  "Split in folders of " + filesPerDir + " files" + super.toString();
    }
}
