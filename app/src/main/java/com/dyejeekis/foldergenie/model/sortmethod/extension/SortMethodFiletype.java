package com.dyejeekis.foldergenie.model.sortmethod.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupVideo;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortMethodFiletype extends SortMethod {

    private final List<ExtensionGroup> extensionGroups;

    public SortMethodFiletype(boolean addToArchive, boolean addToFilename) {
        super(addToArchive, addToFilename);
        this.extensionGroups = new ArrayList<>();
        this.extensionGroups.add(new ExtensionGroup(Arrays.asList(FileGroupAudio.EXTENSIONS)));
        this.extensionGroups.add(new ExtensionGroup(Arrays.asList(FileGroupVideo.EXTENSIONS)));
        this.extensionGroups.add(new ExtensionGroup(Arrays.asList(FileGroupImage.EXTENSIONS)));
        this.extensionGroups.add(new ExtensionGroup(Arrays.asList(FileGroupDocument.EXTENSIONS)));
    }

    @Override
    public String getDirName(File file) {
        for (int i=0; i<extensionGroups.size(); i++) {
            if (extensionGroups.get(i).contains(GeneralUtil.getFileExtension(file))) {
                switch (i) {
                    case 0:
                        return SortMethodAudio.DIR_NAME;
                    case 1:
                        return SortMethodVideo.DIR_NAME;
                    case 2:
                        return SortMethodImage.DIR_NAME;
                    case 3:
                        return SortMethodDocument.DIR_NAME;
                }
            }
        }
        return "";
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on file type (audio, video, image, document)";
        return s + super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.FILETYPE;
    }
}
