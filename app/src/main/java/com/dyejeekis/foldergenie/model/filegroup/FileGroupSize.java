package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.SizeRange;

import java.io.File;

public class FileGroupSize extends FileGroup {

    private final SizeRange sizeRange;

    public FileGroupSize(boolean includeSubdirs, SizeRange sizeRange) {
        super(includeSubdirs);
        this.sizeRange = sizeRange;
    }

    @Override
    public File[] listfiles(File dir) {
        // TODO: 7/27/2021
        return new File[0];
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.SIZE;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "All files ranging in size " + sizeRange.toString();
        return s + super.toString();
    }

    public SizeRange getSizeRange() {
        return sizeRange;
    }
}
