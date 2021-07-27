
package com.dyejeekis.foldergenie.model.operation;

import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderSort extends FolderOperation {

    public static final String TAG = FolderSort.class.getSimpleName();

    private final FileGroup fileGroup;
    private final List<SortMethod> sortMethods;

    public FolderSort(File rootDir, FileGroup fileGroup, List<SortMethod> sortMethods) {
        super(rootDir);
        this.fileGroup = fileGroup;
        this.sortMethods = sortMethods;
    }

    private FolderSort(Builder builder) {
        super(builder.rootDir);
        this.fileGroup = builder.fileGroup;
        this.sortMethods = builder.sortMethods;
    }

    public FileGroup getFileGroup() {
        return fileGroup;
    }

    public List<SortMethod> getSortMethods() {
        return sortMethods;
    }

    // rename each sorted file based on sort method (ignore for alphanumeric sorting?)
    public boolean renameFiles() {
        for (SortMethod sortMethod : this.sortMethods) {
            if (sortMethod.addToFilename()) return true;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Root directory: " + rootDir.getAbsolutePath() + "\nTarget file group: " +
                fileGroup.toString() + " (" + fileGroup.listfiles(rootDir).length +
                ")\nRename files: " + renameFiles() + "\nSort methods: ";
        for (int i=0; i<sortMethods.size(); i++) {
            s = s.concat(sortMethods.get(i).toString());
            if (i < sortMethods.size()-1) s = s.concat(" -THEN- ");
        }
        return s;
    }

    @Override
    public boolean startOperation(ResultReceiver resultReceiver) {
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

                String message = "Renaming " + f.getAbsolutePath() + " to " + newPath.getAbsolutePath();
                Log.d(TAG, message);
                onOperationProgress(resultReceiver, message);

                GeneralUtil.rename(f, newPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            onOperationProgress(resultReceiver, "Operation failed to complete\n" + e.toString());
            return false;
        }
        onOperationProgress(resultReceiver, "Operation completed successfully");
        return true;
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