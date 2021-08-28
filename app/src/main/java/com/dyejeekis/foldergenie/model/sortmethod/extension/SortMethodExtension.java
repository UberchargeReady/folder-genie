package com.dyejeekis.foldergenie.model.sortmethod.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMethodExtension extends SortMethod {

    private final List<ExtensionGroup> extensionGroups;

    public SortMethodExtension(@NonNull List<ExtensionGroup> extensionGroups, boolean addToArchive,
                               boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (extensionGroups.isEmpty())
            throw new IllegalArgumentException("Sort method must have at least one extension group to be valid");
        if (ExtensionGroup.groupsOverlap(extensionGroups))
            throw new IllegalArgumentException("Overlap detected in selected extension groups");
        this.extensionGroups = extensionGroups;
    }

    @Override
    public boolean addToFilename() {
        return false;
    }

    @Override
    public String getDirName(File file) {
        for (ExtensionGroup group : extensionGroups) {
            if (group.contains(GeneralUtil.getFileExtension(file)))
                return group.toString();
        }
        return "";
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Sort in folders based on file extension ";
        s = s.concat("'" + GeneralUtil.listToString(extensionGroups, "', '") + "'");
        return s + super.toString();
    }

    @Override
    public SortMethodType getType() {
        return SortMethodType.EXTENSION;
    }

}
