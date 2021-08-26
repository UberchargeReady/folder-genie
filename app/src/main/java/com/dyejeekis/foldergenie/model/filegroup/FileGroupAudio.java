package com.dyejeekis.foldergenie.model.filegroup;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;

import java.util.Arrays;
import java.util.List;

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
        super(new ExtensionGroup(Arrays.asList(AUDIO_EXTENSIONS)), includeSubdirs);
    }

    @NonNull
    @Override
    public String toString() {
        return "All audio files";
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.AUDIO;
    }
}
