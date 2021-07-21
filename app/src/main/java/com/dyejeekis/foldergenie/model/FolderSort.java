
package com.dyejeekis.foldergenie.model;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.service.ServiceResultReceiver;
import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FolderSort implements Serializable {

    public static final String TAG = FolderSort.class.getSimpleName();

    private final File rootDir;
    private final FileGroup fileGroup;
    private final List<SortMethod> sortMethods; // TODO: 7/11/2021 properly handle multiple sort methods of the same type

    private boolean renameFiles; // rename each sorted file based on sort method (ignore for alphanumeric sorting?)

    private FolderSort(Builder builder) {
        this.rootDir = builder.rootDir;
        this.fileGroup = builder.fileGroup;
        this.sortMethods = builder.sortMethods;
        for (SortMethod sortMethod : this.sortMethods) {
            if (sortMethod.addToFilename()) {
                renameFiles = true;
                break;
            }
        }
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
            if (i < sortMethods.size()-1) s = s.concat(" -THEN- ");
        }
        return s;
    }

    // returns true on successful sort completion
    public boolean executeSort(ResultReceiver resultReceiver) {
        try {
            File[] files = fileGroup.listfiles(rootDir);
            for (File f : files) {

                // calculate new directory based on sort methods
                File targetDir = f.getParentFile();
                for (SortMethod m : sortMethods) {
                    targetDir = m.getTargetDir(f, targetDir);
                }

                // create directories if they don't exist yet
                if (!targetDir.exists()) {
                    Log.d(TAG, "Creating target directory " + targetDir.getAbsolutePath());
                    targetDir.mkdirs();
                }

                // move (rename) file to new directory
                File newPath = new File(targetDir.getAbsolutePath() + File.separator +
                        f.getName());

                String msg = "Renaming " + f.getAbsolutePath() + " to " + newPath.getAbsolutePath();
                Log.d(TAG, msg);

                if (resultReceiver != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE, msg);
                    resultReceiver.send(ServiceResultReceiver.CODE_SHOW_PROGRESS, bundle);
                }

                GeneralUtil.rename(f, newPath);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (resultReceiver != null) {
                Bundle bundle = new Bundle();
                bundle.putString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE,
                        "Operation failed to complete\n" + e.toString());
                resultReceiver.send(ServiceResultReceiver.CODE_SHOW_PROGRESS, bundle);
            }

            return false;
        }

        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE,
                    "Operation completed successfully");
            resultReceiver.send(ServiceResultReceiver.CODE_SHOW_PROGRESS, bundle);
        }

        return true;
    }

    public boolean executeSort() {
        return executeSort(null);
    }

    public static class Builder {

        private File rootDir;
        private FileGroup fileGroup;
        private final List<SortMethod> sortMethods;

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

        public Builder addSortMethods(List<SortMethod> sortMethods) {
            this.sortMethods.addAll(sortMethods);
            return this;
        }

        public Builder addSortMethod(SortMethod sortMethod) {
            this.sortMethods.add(sortMethod);
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
