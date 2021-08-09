package com.dyejeekis.foldergenie.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class GeneralUtil {

    public static final String TAG = GeneralUtil.class.getSimpleName();

    public static List<File> listFilesRecursive(File dir, FileFilter filter) {
        List<File> resultList = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isFile() && (filter == null || filter.accept(f)))
                resultList.add(f);
            else if (f.isDirectory()) {
                List<File> newFiles = listFilesRecursive(f, filter);
                resultList.addAll(newFiles);
            }
        }
        return resultList;
    }

    public static String getFileExtension(@NonNull File file) throws IllegalArgumentException {
        if (file.isFile()) {
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                return file.getName().substring(i+1);
            } else throw new IllegalArgumentException("File name doesn't have an extension: " +
                    file.getAbsolutePath());
        }
        throw new IllegalArgumentException("Argument is not a file: " + file.getAbsolutePath());
    }

    public static final Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");

    public static boolean isValidFileExtension(@NonNull String extension) {
        return !specialCharPattern.matcher(extension).find();
    }

    public static boolean rename(File from, File to) {
        return from.getParentFile()!= null && from.getParentFile().exists()
                && from.exists() && from.renameTo(to);
    }

    public static Date getRandomDate() {
        return new Date(getRandomTimestamp());
    }

    public static long getRandomTimestamp() {
        return ThreadLocalRandom.current().nextLong(System.currentTimeMillis());
    }

    public static int getDayNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayString(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }

    public static String listToString(List<?> list, String separator) {
        String string = "";
        for (Object o : list) {
            string = string.concat(o.toString());
            if (list.indexOf(o) < list.size() - 1) string = string.concat(separator);
        }
        return string;
    }

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String getReadableFilesize(long bytes) {
        final int decimalPlaces = 1;
        if (bytes >= 1000000000) {
            return round((double) bytes / 1000000000, decimalPlaces) + "GB";
        }
        if (bytes >= 1000000) {
            return round((double) bytes / 1000000, decimalPlaces) + "MB";
        }
        if (bytes >= 1000) {
            return round((double) bytes / 1000, decimalPlaces) + "KB";
        }
        return bytes + "B";
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    // returns index of nth occurrence of substr in str
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}
