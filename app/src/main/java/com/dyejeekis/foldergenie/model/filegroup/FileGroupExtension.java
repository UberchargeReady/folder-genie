package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileGroupExtension extends FileGroup {

    private final List<String> extensions;

    public FileGroupExtension(@NonNull List<String> extensions, boolean includeSubdirs) {
        super(includeSubdirs);
        if (extensions.isEmpty())
            throw new IllegalArgumentException("File group must have at least one extension to be valid");
        this.extensions = extensions;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public File[] listFiles(File dir) {
        if (includeSubdirs()) {
            List<File> fileList = GeneralUtil.listFilesRecursive(dir, file -> {
                try {
                    String extension = GeneralUtil.getFileExtension(file);
                    return getExtensions().contains(extension);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });
            return fileList.toArray(new File[0]);
        }
        return dir.listFiles(file -> {
            try {
                String extension = GeneralUtil.getFileExtension(file);
                return getExtensions().contains(extension);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    @NonNull
    @Override
    public String toString() {
        return "Files with extensions " + GeneralUtil.listToString(getExtensions(), ",");
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.FILE_EXTENSION;
    }
}
