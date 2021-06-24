package com.dyejeekis.foldergenie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class Util {

    public static File[] listFilesRecursive(File dir) {
        // TODO: 5/30/2021
        return null;
    }

    public static String getFileExtension(File file) throws IllegalArgumentException {
        if (file.isFile()) {
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                return file.getName().substring(i+1);
            } else throw new IllegalArgumentException("File name doesn't have an extension");
        }
        throw new IllegalArgumentException("Argument is not a file");
    }

    public static boolean rename(File from, File to) {
        return from.getParentFile()!= null && from.getParentFile().exists()
                && from.exists() && from.renameTo(to);
    }

    public static File[] generateDummyFiles(File parentDir, int fileCount) throws IOException {
        // TODO: 6/5/2021 add random file extensions
        File[] files = new File[fileCount];
        for (int i=0; i<fileCount; i++) {
            String name = UUID.randomUUID().toString();
            File file = new File(parentDir.getAbsolutePath() + File.separator + name);
            file.setLastModified(getRandomTimestamp());
            boolean success = file.createNewFile();
            if (success) files[i] = file;
        }
        return files;
    }

    public static Date getRandomDate() {
        return new Date(getRandomTimestamp());
    }

    public static long getRandomTimestamp() {
        Random rnd = new Random();
        return Math.abs(System.currentTimeMillis() - rnd.nextLong());
    }

//    public void setFileCreationDate(String filePath, Date creationDate) throws IOException{
//
//        BasicFileAttributeView attributes = Files.getFileAttributeView(Paths.get(filePath), BasicFileAttributeView.class);
//        FileTime time = FileTime.fromMillis(creationDate.getTime());
//        attributes.setTimes(time, time, time);
//
//    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayStringOld(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }

//    public static int getDayNumberNew(LocalDate date) {
//        DayOfWeek day = date.getDayOfWeek();
//        return day.getValue();
//    }
//
//    public static String getDayStringNew(LocalDate date, Locale locale) {
//        DayOfWeek day = date.getDayOfWeek();
//        return day.getDisplayName(TextStyle.FULL, locale);
//    }

    public static String listToString(List<String> list) {
        String string = "";
        for (String s : list) {
            string = string.concat(s);
            if (list.indexOf(s) < list.size()-1) string = string.concat(", ");
        }
        return string;
    }
}
