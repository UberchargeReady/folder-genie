package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.DateRange;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileGroupDate extends FileGroup {

    private final List<DateRange> dateRanges;

    public FileGroupDate(List<DateRange> dateRanges, boolean includeSubdirs) {
        super(includeSubdirs);
        this.dateRanges = dateRanges;
    }

    private boolean belongsInRange(File file, DateRange dateRange) {
        if (dateRange.getRangeType() == DateRange.RANGE_TYPE_CREATED) {
            // file creation date not available in File class
            throw new IllegalArgumentException("Range type 'created' currently unsupported");
        }
        return dateRange.belongsInRange(file.lastModified());
    }

    @Override
    public File[] listFiles(File dir) {
        FileFilter filter = file -> {
            for (DateRange dateRange : dateRanges) {
                if (belongsInRange(file, dateRange)) return true;
            }
            return false;
        };
        if (includeSubdirs()) return GeneralUtil.listFilesRecursive(dir, filter).toArray(new File[0]);
        return dir.listFiles(filter);
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Files ranging in date " + GeneralUtil.listToString(dateRanges, ", ");
        return s + super.toString();
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.DATE;
    }

    public List<DateRange> getDateRanges() {
        return dateRanges;
    }
}
