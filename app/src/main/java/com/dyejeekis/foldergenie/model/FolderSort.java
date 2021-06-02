package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderSort {

    private final File rootDir;
    private final FileGroup fileGroup;
    private final List<SortMethod> sortMethods;

    // TODO: 5/30/2021 maybe redundant (replaced by SortMethod field), consider removing later
    private final boolean renameFiles; // rename each sorted file based on sort method (ignore for alphanumeric sorting?)

    private FolderSort(Builder builder) {
        this.rootDir = builder.rootDir;
        this.fileGroup = builder.fileGroup;
        this.sortMethods = builder.sortMethods;
        this.renameFiles = builder.renameFiles;
    }

    public File getRootDir() {
        return rootDir;
    }

    public FileGroup getFileGroup() {
        return fileGroup;
    }

    public List<SortMethod> getSortMethods() {
        return sortMethods;
    }

    public boolean isRenameFiles() {
        return renameFiles;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Root directory: " + rootDir.getAbsolutePath() + "\nTarget file group: " +
                fileGroup.toString() + "\nRename files: " + renameFiles + "\nSort methods: ";
        for (int i=0; i<sortMethods.size(); i++) {
            s = s.concat(sortMethods.get(i).toString());
            if (i < sortMethods.size()-1) s = s.concat(", ");
        }
        return s;
    }

    // returns true on successful sort completion
    public boolean executeSort() {
        try {
            // TODO: 5/28/2021
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static class Builder {

        private File rootDir;
        private FileGroup fileGroup;
        private final List<SortMethod> sortMethods;
        private boolean renameFiles;

        public Builder() {
            sortMethods = new ArrayList<>();
        }

        public Builder rootDir(File rootDir) {
            this.rootDir = rootDir;
            return this;
        }

        public Builder fileGroup(FileGroup fileGroup) {
            this.fileGroup = fileGroup;
            return this;
        }

        public Builder addSortMethod(SortMethod sortMethod) {
            this.sortMethods.add(sortMethod);
            return this;
        }

        public Builder renameFiles(boolean renameFiles) {
            this.renameFiles = renameFiles;
            return this;
        }

        public FolderSort build() {
            FolderSort folderSort = new FolderSort(this);
            validateFolderSort(folderSort);
            return folderSort;
        }

        private void validateFolderSort(FolderSort folderSort) {
            // TODO: 5/28/2021 do some basic validations
        }
    }
}
