package com.dyejeekis.foldergenie.model.filegroup;

import java.io.File;

public class FileGroupDate extends FileGroup {

    public FileGroupDate(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        // TODO: 5/30/2021
        return new File[0];
    }
}
