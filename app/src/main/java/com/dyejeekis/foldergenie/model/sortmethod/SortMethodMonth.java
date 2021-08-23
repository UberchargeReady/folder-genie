package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.Calendar;

public class SortMethodMonth extends SortMethod {

    public SortMethodMonth(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
    }

    @Override
    public String getDirName(File file) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        return GeneralUtil.getMonthString(cal.get(Calendar.MONTH) + 1);
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.MONTH;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on month";
        return s + super.toString();
    }
}
