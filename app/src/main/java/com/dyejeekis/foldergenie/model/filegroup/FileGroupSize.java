package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.OverlappingElementsException;
import com.dyejeekis.foldergenie.model.SizeRange;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileGroupSize extends FileGroup {

    private final List<SizeRange> sizeRanges;

    public FileGroupSize(List<SizeRange> sizeRanges, boolean includeSubdirs) {
        super(includeSubdirs);
        if (sizeRanges.isEmpty())
            throw new IllegalArgumentException("File group must have at least one size range to be valid");
        if (SizeRange.rangesOverlap(sizeRanges))
            throw new OverlappingElementsException("Overlap detected in selected size ranges");
        this.sizeRanges = sizeRanges;
    }

    private boolean belongsInRange(File file, SizeRange sizeRange) {
        if (file.length() >= sizeRange.getMin())
            return sizeRange.getMax() <= 0 || file.length() <= sizeRange.getMax();
        return false;
    }

    @Override
    public File[] listFiles(File dir) {
        FileFilter filter = file -> {
            for (SizeRange sizeRange : sizeRanges) {
                if (belongsInRange(file, sizeRange)) return true;
            }
            return false;
        };
        if (includeSubdirs()) return GeneralUtil.listFilesRecursive(dir, filter).toArray(new File[0]);
        return dir.listFiles(filter);
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.SIZE;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Files ranging in size " + GeneralUtil.listToString(sizeRanges, ", ");
        return s + super.toString();
    }
}
