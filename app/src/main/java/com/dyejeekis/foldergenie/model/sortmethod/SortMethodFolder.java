package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import java.io.File;

public class SortMethodFolder extends SortMethod{

    private final String name;

    public SortMethodFolder(String name, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        this.name = name;
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public String getDirName(File file) {
        return name;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.FOLDER;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Move files into '" + name + "'";
        return s + super.toString();
    }

    public String getName() {
        return name;
    }
}
