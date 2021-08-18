package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.DateRange;

import java.io.File;

public class FileGroupDate extends FileGroup {

    private DateRange dateRange;

    public FileGroupDate(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public File[] listFiles(File dir) {
        // TODO: 5/30/2021
        return new File[0];
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 6/6/2021
        return super.toString();
    }

    @Override
    public FileGroupType getType() {
        // TODO: 6/12/2021
        return null;
    }
}
