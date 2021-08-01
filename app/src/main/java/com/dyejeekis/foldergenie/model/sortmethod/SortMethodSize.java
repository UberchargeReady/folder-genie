package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.SizeRange;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMethodSize extends SortMethod {

    // TODO: 7/11/2021 think about how to handle overlap in size ranges
    private final List<SizeRange> sizeRanges;

    public SortMethodSize(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        sizeRanges = new ArrayList<>();
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
        for (SizeRange sizeRange : sizeRanges) {
            s = s.concat(sizeRange.toString());
            if (sizeRanges.indexOf(sizeRange) < sizeRanges.size() - 1)
                s = s.concat(", ");
        }
        return s  + super.toString();
    }

    public List<SizeRange> getSizeRanges() {
        return sizeRanges;
    }

    public void addSizeRange(SizeRange sizeRange) {
        sizeRanges.add(sizeRange);
    }
}
