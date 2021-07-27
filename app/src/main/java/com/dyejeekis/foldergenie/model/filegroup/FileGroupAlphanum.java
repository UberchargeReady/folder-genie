package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.AlphanumRange;

import java.io.File;

public class FileGroupAlphanum extends FileGroup {

    private final AlphanumRange alphanumRange;

    public FileGroupAlphanum(boolean includeSubdirs, AlphanumRange alphanumRange) {
        super(includeSubdirs);
        this.alphanumRange = alphanumRange;
    }

    @Override
    public File[] listfiles(File dir) {
        // TODO: 5/30/2021
        return new File[0];
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.ALPHANUMERIC;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "All files in alphanumeric range " + alphanumRange.toString();
        return s + super.toString();
    }

    public AlphanumRange getAlphanumRange() {
        return alphanumRange;
    }
}
