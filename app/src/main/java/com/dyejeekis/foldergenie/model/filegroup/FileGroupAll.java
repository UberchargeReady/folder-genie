package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.List;

public class FileGroupAll extends FileGroup {

    public FileGroupAll(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        if (includeSubdirs()) {
            return GeneralUtil.listFilesRecursive(dir, null).toArray(new File[0]);
        }
        return dir.listFiles(File::isFile);
    }

    @NonNull
    @Override
    public String toString() {
        return "All files in directory" + super.toString();
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.ALL;
    }
}
