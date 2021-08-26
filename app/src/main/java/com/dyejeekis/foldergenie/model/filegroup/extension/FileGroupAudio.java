package com.dyejeekis.foldergenie.model.filegroup.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import java.util.Arrays;

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
