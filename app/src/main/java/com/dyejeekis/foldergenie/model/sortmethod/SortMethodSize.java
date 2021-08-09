package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.util.SizeRange;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMethodSize extends SortMethod {

    private final List<SizeRange> sizeRanges;

    public SortMethodSize(@NonNull List<SizeRange> sizeRanges, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (sizeRanges.isEmpty())
            throw new IllegalArgumentException("Sort method must have at least one size range to be valid");
        if (rangesOverlap(sizeRanges))
            throw new IllegalArgumentException("Overlap detected in selected size ranges");
        this.sizeRanges = sizeRanges;
    }

    @Override
    public String getDirName(File file) {
        for (SizeRange range : sizeRanges) {
            if (file.length() >= range.getMinSize()) {
                if (range.getMaxSize() <= 0 || file.length() <= range.getMaxSize())
                    return range.toString();
            }
        }
        return "";
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
        String s = "Sort in folders based on file size ";
        s = s.concat(GeneralUtil.listToString(sizeRanges, ", "));
        return s + super.toString();
    }

    public List<SizeRange> getSizeRanges() {
        return sizeRanges;
    }

    private boolean rangesOverlap(List<SizeRange> sizeRanges) {
        // TODO: 8/9/2021
        return false;
    }
}
