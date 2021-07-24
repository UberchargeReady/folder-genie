package com.dyejeekis.foldergenie.model;

import android.os.ResultReceiver;
import android.util.Log;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAll;

import java.io.File;
import java.io.Serializable;

public class FolderFlatten implements Serializable {

    public static final String TAG = FolderFlatten.class.getSimpleName();

    private File rootDir;
    private FileGroup fileGroup;

    public FolderFlatten(File rootDir) {
        this.rootDir = rootDir;
        this.fileGroup = new FileGroupAll(true);
    }

    public FolderFlatten(File rootDir, FileGroup fileGroup) {
        this.rootDir = rootDir;
        this.fileGroup = fileGroup;
        this.fileGroup.setIncludeSubdirs(true);
    }

    // return true on successful completion of operation
    public boolean executeFlatten(ResultReceiver resultReceiver) {
        try {
            // TODO: 7/23/2021
            File[] files = fileGroup.listfiles(rootDir);
            for (File f : files) {
                // move (rename) file to root directory
                File newPath = new File(rootDir.getAbsolutePath() + File.separator +
                        f.getName());

                String msg = "Renaming " + f.getAbsolutePath() + " to " + newPath.getAbsolutePath();
                Log.d(TAG, msg);

                if (resultReceiver != null) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (resultReceiver != null) {

            }

            return false;
        }

        if (resultReceiver != null) {

        }

        return true;
    }

    public boolean executeFlatten() {
        return executeFlatten(null);
    }
}
