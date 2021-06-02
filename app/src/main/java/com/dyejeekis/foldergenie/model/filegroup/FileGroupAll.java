package com.dyejeekis.foldergenie.model.filegroup;

import com.dyejeekis.foldergenie.Util;

import java.io.File;
import java.io.FileFilter;

public class FileGroupAll extends FileGroup {

    public FileGroupAll(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        if (includeSubdirs()) {
            return Util.listFilesRecursive(dir);
        }
        return dir.listFiles(File::isFile);
    }
}
