package com.dyejeekis.foldergenie.model.filegroup;

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
        super(includeSubdirs);
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.VIDEO;
    }
}
