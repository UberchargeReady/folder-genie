package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;

public class FileGroupAll extends FileGroup {

    public FileGroupAll(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        if (includeSubdirs()) {
            return GeneralUtil.listFilesRecursive(dir); // TODO: 6/5/2021 this isn't implemented yet
        }
        return dir.listFiles(File::isFile);
    }

    @NonNull
    @Override
    public String toString() {
        return "All files in directory";
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.ALL;
    }
}
