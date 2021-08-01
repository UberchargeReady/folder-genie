package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileGroupDocument extends FileGroupExtension {

    public static String[] DOCUMENT_EXTENSIONS = {
            "doc", "docx",
            "html", "htm",
            "odt",
            "pdf",
            "xls", "xlsx",
            "ods",
            "ppt", "pptx",
            "txt",

    };

    public FileGroupDocument(boolean includeSubdirs) {
        super(includeSubdirs);
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList(DOCUMENT_EXTENSIONS);
    }

    @NonNull
    @Override
    public String toString() {
        return "All documents";
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.DOCUMENT;
    }
}
