package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.AlphanumRange;

import java.io.File;

public class FileGroupAlphanum extends FileGroup {

    private final AlphanumRange alphanumRange;

    public FileGroupAlphanum(AlphanumRange alphanumRange, boolean includeSubdirs) {
        super(includeSubdirs);
        this.alphanumRange = alphanumRange;
    }

    @Override
    public File[] listFiles(File dir) {
        return dir.listFiles(file -> {
            String start = alphanumRange.getStartStr();
            String end = alphanumRange.getEndStr();
            if (start == null || file.getName().compareTo(start) >= 0)
                return end == null || file.getName().compareTo(end) <= 0;
            return false;
        });
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.ALPHANUMERIC;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "All files with names " + alphanumRange.toString();
        return s + super.toString();
    }

    public AlphanumRange getAlphanumRange() {
        return alphanumRange;
    }
}
