package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.RangeOverlapException;
import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.model.SizeRange;

import java.io.File;
import java.util.List;

public class SortMethodSize extends SortMethod {

    private final List<SizeRange> sizeRanges;

    public SortMethodSize(@NonNull List<SizeRange> sizeRanges, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (sizeRanges.isEmpty())
            throw new IllegalArgumentException("Sort method must have at least one size range to be valid");
        if (SizeRange.rangesOverlap(sizeRanges))
            throw new RangeOverlapException("Overlap detected in selected size ranges");
        this.sizeRanges = sizeRanges;
    }

    @Override
    public String getDirName(File file) {
        for (SizeRange range : sizeRanges) {
            if (file.length() >= range.getMin()) {
                if (range.getMax() <= 0 || file.length() <= range.getMax())
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
        s = s.concat("'" + GeneralUtil.listToString(sizeRanges, "', '") + "'");
        return s + super.toString();
    }
}
