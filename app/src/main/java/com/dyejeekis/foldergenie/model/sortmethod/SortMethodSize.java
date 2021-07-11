package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMethodSize extends SortMethod {

    public static class SizeRange {
        final long minSize, maxSize;

        SizeRange(long minSize, long maxSize) {
            if ((minSize <= 0 && maxSize <= 0) || minSize > maxSize)
                throw new IllegalArgumentException("Invalid size range");
            this.minSize = minSize;
            this.maxSize = maxSize;
        }

        @NonNull
        @Override
        public String toString() {
            String minS = GeneralUtil.getReadableFilesize(minSize);
            String maxS = GeneralUtil.getReadableFilesize(maxSize);
            if (minSize == maxSize) return minS;
            if (minSize <= 0) return "<=" + maxS;
            if (maxSize <= 0) return ">=" + minS;
            return minS + "-" + maxS;
        }
    }

    // TODO: 7/11/2021 think about how to handle overlap in size ranges
    private final List<SizeRange> sizeRanges;

    public SortMethodSize(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        sizeRanges = new ArrayList<>();
    }

    @Override
    public String getDirName(File file) {
        return null;
    }

    @Override
    public File getTargetDir(File file, File parentDir) {
        return null;
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.SIZE;
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on file size - Folders: ";
        for (SizeRange sizeRange : sizeRanges) {
            s = s.concat(sizeRange.toString());
            if (sizeRanges.indexOf(sizeRange) < sizeRanges.size()-1)
                s = s.concat(", ");
        }
        return s  + super.toString();
    }

    public List<SizeRange> getSizeRanges() {
        return sizeRanges;
    }

    public void addSizeRange(long minSize, long maxSize) {
        sizeRanges.add(new SizeRange(minSize, maxSize));
    }

    public void addSizeRange(SizeRange sizeRange) {
        sizeRanges.add(sizeRange);
    }
}
