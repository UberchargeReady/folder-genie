package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.AlphanumRange;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMethodAlphanum extends SortMethod {

    // TODO: 7/11/2021 think about how to handle overlap in alphanumeric ranges
    private final List<AlphanumRange> alphanumRanges;

    public SortMethodAlphanum(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        alphanumRanges = new ArrayList<>();
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public String getDirName(File file) {
        for (AlphanumRange range : alphanumRanges) {
            String start = range.getStartStr();
            String end = range.getEndStr();
            if (start == null || file.getName().compareTo(start) >= 0) {
                if (end == null || file.getName().compareTo(end) <= 0)
                    return range.toString();
            }
        }
        return "";
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.ALPHANUMERIC;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on alphanumeric ranges ";
        for (AlphanumRange range : alphanumRanges) {
            s = s.concat(range.toString());
            if (alphanumRanges.indexOf(range) < alphanumRanges.size() - 1)
                s = s.concat(", ");
        }
        return s + super.toString();
    }

    public List<AlphanumRange> getAlphanumRanges() {
        return alphanumRanges;
    }

    public void addAlphanumRange(AlphanumRange alphanumRange) {
        alphanumRanges.add(alphanumRange);
    }
}
