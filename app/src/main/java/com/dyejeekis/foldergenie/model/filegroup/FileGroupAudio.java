package com.dyejeekis.foldergenie.model.filegroup;

import java.io.File;

public class FileGroupAudio extends FileGroupExtension {

    public static String[] AUDIO_EXTENSIONS = {
            "pcm",
            "wav",
            "aiff",
            "mp3",
            "aac",
            "ogg",
            "wma",
            "flac",
            "alac"
    };

    public FileGroupAudio(boolean includeSubdirs) {
        super(includeSubdirs);
    }
}
