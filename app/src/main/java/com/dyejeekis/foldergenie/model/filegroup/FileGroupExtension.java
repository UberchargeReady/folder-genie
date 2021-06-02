package com.dyejeekis.foldergenie.model.filegroup;

import com.dyejeekis.foldergenie.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileGroupExtension extends FileGroup {

    private final List<String> extensions;

    public FileGroupExtension(boolean includeSubdirs) {
        super(includeSubdirs);
        this.extensions = new ArrayList<>();
    }

    public void addExtension(String extension) {
        this.extensions.add(extension);
    }

    @Override
    public File[] listfiles(File dir) {
        // TODO: 5/30/2021 include sub-dirs
        return dir.listFiles(pathname -> {
            try {
                String e = Util.getFileExtension(pathname);
                return extensions.contains(e);
            } catch (IllegalArgumentException e) {}
            return false;
        });
    }
}
