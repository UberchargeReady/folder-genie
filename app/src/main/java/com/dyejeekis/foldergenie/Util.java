package com.dyejeekis.foldergenie;

import java.io.File;

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
}
