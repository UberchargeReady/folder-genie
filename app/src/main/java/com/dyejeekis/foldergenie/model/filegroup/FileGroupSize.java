package com.dyejeekis.foldergenie.model.filegroup;

import java.io.File;

public class FileGroupSize extends FileGroup {

    public FileGroupSize(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        return new File[0];
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.SIZE;
    }
}
