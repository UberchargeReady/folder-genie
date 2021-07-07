package com.dyejeekis.foldergenie.util;

import android.net.Uri;
import android.util.Log;

import com.dyejeekis.foldergenie.model.filegroup.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupVideo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class GeneralUtil {

    public static final String TAG = "GeneralUtil";

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

    public static File[] generateDummyFiles(File parentDir, int fileCount) {
        File[] files = new File[fileCount];
        List<String> extensions = getExtensionsList();
        try {
            for (int i = 0; i < fileCount; i++) {
                String name = UUID.randomUUID().toString();
                name = name.concat(getRandomFileExtension(extensions));
                File file = new File(parentDir.getAbsolutePath() + File.separator + name);
                file.setLastModified(getRandomTimestamp());
                boolean success = file.createNewFile();
                if (success) {
                    Log.d(TAG, "Generated file " + file.getAbsolutePath());
                    files[i] = file;
                } else Log.d(TAG, "Failed to create file " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static List<String> getExtensionsList() {
        List<String> extensions = new ArrayList<>();
        extensions.addAll(Arrays.asList(FileGroupImage.IMAGE_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupVideo.VIDEO_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupAudio.AUDIO_EXTENSIONS));
        extensions.addAll(Arrays.asList(FileGroupDocument.DOCUMENT_EXTENSIONS));
        return extensions;
    }

    public static String getRandomFileExtension(List<String> extensions) {
        Random random = new Random();
        return extensions.get(random.nextInt(extensions.size()));
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

//    public static File uriToFile(Uri uri) {
//        try {
//            return new File(new URI(uri.getPath()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
