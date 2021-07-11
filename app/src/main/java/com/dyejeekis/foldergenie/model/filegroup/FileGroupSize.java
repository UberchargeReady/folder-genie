package com.dyejeekis.foldergenie.model.filegroup;

import java.io.File;

public class FileGroupSize extends FileGroup {

    private final long minSize, maxSize;

    public FileGroupSize(boolean includeSubdirs, long minSize, long maxSize) {
        super(includeSubdirs);
        if (minSize <= 0 && maxSize <= 0) throw new IllegalArgumentException("Invalid size range");
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public File[] listfiles(File dir) {
        return new File[0];
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.SIZE;
    }

    public long getMinSize() {
        return minSize;
    }

    public long getMaxSize() {
        return maxSize;
    }
}
