package com.dyejeekis.foldergenie.model.sortmethod;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SortMethodExtension extends SortMethod {

    private final List<ExtensionGroup> extensionGroups;

    public SortMethodExtension(@NonNull List<ExtensionGroup> extensionGroups, boolean addToArchive,
                               boolean addToFilename) {
        super(addToArchive, addToFilename);
        if (extensionGroups.isEmpty())
            throw new IllegalArgumentException("Sort method must have at least one extension group to be valid");
        if (groupsOverlap(extensionGroups))
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

    public List<ExtensionGroup> getExtensionGroups() {
        return extensionGroups;
    }

    private boolean groupsOverlap(List<ExtensionGroup> extensionGroups) {
        // TODO: 8/9/2021
        return false;
    }

    public static class ExtensionGroup implements Serializable {

        List<String> extensions;

        public ExtensionGroup(@NonNull List<String> extensions) {
            if (extensions.isEmpty())
                throw new IllegalArgumentException("Extension group can't be empty");
            if (!validateExtensions(extensions))
                throw new IllegalArgumentException("Extension group (" + toString()
                        + ") contains invalid extension(s)");
            if (containsDuplicates(extensions))
                throw new IllegalArgumentException("Extension group contains duplicate extensions");
            this.extensions = extensions;
        }

        public boolean contains(String extension) {
            return this.extensions.contains(extension);
        }

        public boolean overlapsWith(ExtensionGroup extensionGroup) {
            // TODO: 8/9/2021
            return false;
        }

        private boolean validateExtensions(List<String> extensions) {
            for (String extension : extensions) {
                if (!GeneralUtil.isValidFileExtension(extension)) return false;
            }
            return true;
        }

        private boolean containsDuplicates(List<String> extensions) {
            // TODO: 8/8/2021
            return false;
        }

        @NonNull
        @Override
        public String toString() {
            return GeneralUtil.listToString(extensions, " ");
        }
    }
}
