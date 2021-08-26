package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;

import java.util.Arrays;
import java.util.List;

public class FileGroupVideo extends FileGroupExtension {

    public static String[] VIDEO_EXTENSIONS = {
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
        super(new ExtensionGroup(Arrays.asList(VIDEO_EXTENSIONS)), includeSubdirs);
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
