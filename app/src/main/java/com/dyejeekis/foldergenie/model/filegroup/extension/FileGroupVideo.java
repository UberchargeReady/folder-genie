package com.dyejeekis.foldergenie.model.filegroup.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import java.util.Arrays;

public class FileGroupVideo extends FileGroupExtension {

    public static String[] EXTENSIONS = {
            "mp4",
            "mov",
            "avi",
            "flv",
            "mkv",
            "wmv",
            "avchd",
            "webm",
            "h264",
            "mpeg4"
    };

    public FileGroupVideo(boolean includeSubdirs) {
        super(new ExtensionGroup(Arrays.asList(EXTENSIONS)), includeSubdirs);
    }

    @NonNull
    @Override
    public String toString() {
        return "All videos";
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.VIDEO;
    }
}
