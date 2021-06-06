package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import java.io.File;

public class FileGroupAlphanum extends FileGroup {

    public FileGroupAlphanum(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listfiles(File dir) {
        // TODO: 5/30/2021
        return new File[0];
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 6/6/2021
        return super.toString();
    }
}
