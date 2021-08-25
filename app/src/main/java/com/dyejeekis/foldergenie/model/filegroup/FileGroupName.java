package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.AlphanumRange;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileGroupName extends FileGroup {

    private final List<AlphanumRange> alphanumRanges;

    public FileGroupName(List<AlphanumRange> alphanumRanges, boolean includeSubdirs) {
        super(includeSubdirs);
        if (alphanumRanges.isEmpty())
            throw new IllegalArgumentException("File group must have at least one alphanumeric range to be valid");
        if (AlphanumRange.rangesOverlap(alphanumRanges))
            throw new IllegalArgumentException("Overlap detected in selected alphanumeric ranges");
        this.alphanumRanges = alphanumRanges;
    }

    private boolean belongsInRange(File file, AlphanumRange alphanumRange) {
        String start = alphanumRange.getFrom();
        String end = alphanumRange.getTo();
        if (start == null || file.getName().compareTo(start) >= 0)
            return end == null || file.getName().compareTo(end) <= 0;
        return false;
    }

    @Override
    public File[] listFiles(File dir) {
        FileFilter filter = file -> {
            for (AlphanumRange alphanumRange : alphanumRanges) {
                if (belongsInRange(file, alphanumRange)) return true;
            }
            return false;
        };
        if (includeSubdirs()) return GeneralUtil.listFilesRecursive(dir, filter).toArray(new File[0]);
        return dir.listFiles(filter);
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.NAME;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Files with names " + GeneralUtil.listToString(alphanumRanges, ", ");
        return s + super.toString();
    }

    public List<AlphanumRange> getAlphanumRanges() {
        return alphanumRanges;
    }
}
