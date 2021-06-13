package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import java.io.File;

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
