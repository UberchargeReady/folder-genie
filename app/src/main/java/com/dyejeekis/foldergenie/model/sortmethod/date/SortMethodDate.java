package com.dyejeekis.foldergenie.model.sortmethod.date;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.DateRange;
import com.dyejeekis.foldergenie.model.OverlappingElementsException;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.List;

public class SortMethodDate extends SortMethod {

    private final List<DateRange> dateRanges;

    public SortMethodDate(List<DateRange> dateRanges, boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (dateRanges.isEmpty())
            throw new IllegalArgumentException("Sort method must have at least one date range to be valid");
        if (DateRange.rangesOverlap(dateRanges))
            throw new OverlappingElementsException("Overlap detected in selected size ranges");
        this.dateRanges = dateRanges;
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    private boolean belongsInRange(File file, DateRange dateRange) {
        if (dateRange.getRangeType() == DateRange.RANGE_TYPE_CREATED) {
            // file creation date not available in File class
            throw new IllegalArgumentException("Range type 'created' currently unsupported");
        }
        return dateRange.belongsInRange(file.lastModified());
    }

    @Override
    public String getDirName(File file) {
        for (DateRange dateRange : dateRanges) {
            if (belongsInRange(file, dateRange)) return dateRange.toString();
        }
        return "";
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.DATE;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on date modified ";
        s = s.concat("'" + GeneralUtil.listToString(dateRanges, "', '") + "'");
        return s + super.toString();
    }
}
