package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.SizeRange;

import java.io.File;

public class FileGroupSize extends FileGroup {

    private final SizeRange sizeRange;

    public FileGroupSize(SizeRange sizeRange, boolean includeSubdirs) {
        super(includeSubdirs);
        this.sizeRange = sizeRange;
    }

    @Override
    public File[] listFiles(File dir) {
        return dir.listFiles(file -> {
            if (file.length() >= sizeRange.getMinSize())
                return sizeRange.getMaxSize() <= 0 || file.length() <= sizeRange.getMaxSize();
            return false;
        });
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
