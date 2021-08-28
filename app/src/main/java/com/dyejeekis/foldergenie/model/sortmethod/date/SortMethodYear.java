package com.dyejeekis.foldergenie.model.sortmethod.date;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;

import java.io.File;
import java.util.Calendar;

public class SortMethodYear extends SortMethod {

    public SortMethodYear(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public String getDirName(File file) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.YEAR;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on year";
        return s + super.toString();
    }

}
