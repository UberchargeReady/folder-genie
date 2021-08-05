package com.dyejeekis.foldergenie.model.operation;

import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

public class FolderClear extends FolderOperation {

    public static final String TAG = FolderClear.class.getSimpleName();

    private final boolean includeSubdirs; // clear empty folders in subdirectories

    public FolderClear(File rootDir, boolean includeSubdirs) {
        super(rootDir);
        this.includeSubdirs = includeSubdirs;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean startOperation(Context context, ResultReceiver resultReceiver, Handler handler) {
        String message = "Folder clear options:\n" + this.toString();
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);

        try {
            deleteEmptyDirs(this.rootDir, resultReceiver);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Failed to delete empty folders in " + rootDir.getAbsolutePath();
            if (resultReceiver == null) onOperationProgress(context, handler, message);
            message = message.concat("\n\n" + e.toString());
            Log.d(TAG, message);
            onOperationProgress(resultReceiver, message);
            return false;
        }
        message = "Empty folders in " + rootDir.getAbsolutePath() + " deleted successfully";
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);
        if (resultReceiver == null) onOperationProgress(context, handler, message);
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 8/5/2021
        return super.toString();
    }

    private void deleteEmptyDirs(File rootDir, ResultReceiver resultReceiver) {
        File[] dirs = rootDir.listFiles(File::isDirectory);
        if (dirs != null) {
            for (File dir : dirs) {
                if (includeSubdirs) deleteEmptyDirs(dir, resultReceiver);
                File[] files = dir.listFiles();
                if (files != null && files.length == 0) {
                    boolean success = dir.delete();
                    String message = success ? "Deleted empty folder " : "Failed to delete empty folder ";
                    message = message.concat(dir.getAbsolutePath());
                    onOperationProgress(resultReceiver, message);
                }
            }
        }
    }
}
