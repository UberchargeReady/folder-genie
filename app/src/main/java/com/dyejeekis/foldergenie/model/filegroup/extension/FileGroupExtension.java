package com.dyejeekis.foldergenie.model.filegroup.extension;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.ExtensionGroup;
import com.dyejeekis.foldergenie.model.OverlappingElementsException;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileGroupExtension extends FileGroup {

    private final List<ExtensionGroup> extensionGroups;

    public FileGroupExtension(@NonNull List<ExtensionGroup> extensionGroups, boolean includeSubdirs) {
        super(includeSubdirs);
        if (extensionGroups.isEmpty())
            throw new IllegalArgumentException("File group must have at least one extension group to be valid");
        if (ExtensionGroup.groupsOverlap(extensionGroups))
            throw new OverlappingElementsException("Overlap detected in selected extension groups");
        this.extensionGroups = extensionGroups;
    }

    public FileGroupExtension(@NonNull ExtensionGroup extensionGroup, boolean includeSubdirs) {
        super(includeSubdirs);
        this.extensionGroups = new ArrayList<>();
        extensionGroups.add(extensionGroup);
    }

    @Override
    public File[] listFiles(File dir) {
        FileFilter filter = file -> {
            try {
                String extension = GeneralUtil.getFileExtension(file);
                for (ExtensionGroup group : extensionGroups) {
                    if (group.contains(extension)) return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
        if (includeSubdirs()) return GeneralUtil.listFilesRecursive(dir, filter).toArray(new File[0]);
        return dir.listFiles(filter);
    }

    @NonNull
    @Override
    public String toString() {
        return "Files with extensions " + GeneralUtil.listToString(extensionGroups, " ");
    }

    @Override
    public FileGroupType getType() {
        return FileGroupType.EXTENSION;
    }
}
