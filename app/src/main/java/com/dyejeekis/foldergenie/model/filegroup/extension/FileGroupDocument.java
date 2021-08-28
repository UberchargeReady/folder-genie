package com.dyejeekis.foldergenie.model.filegroup.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import java.util.Arrays;

public class FileGroupDocument extends FileGroupExtension {

    public static String[] EXTENSIONS = {
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
        super(new ExtensionGroup(Arrays.asList(EXTENSIONS)), includeSubdirs);
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
