package com.dyejeekis.foldergenie.model.operation;

import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAll;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;

public class FolderFlatten extends FolderOperation {

    public static final String TAG = FolderFlatten.class.getSimpleName();

    private final FileGroup fileGroup;

    public FolderFlatten(File rootDir) {
        super(rootDir);
        this.fileGroup = new FileGroupAll(true);
    }

    public FolderFlatten(File rootDir, FileGroup fileGroup) {
        super(rootDir);
        this.fileGroup = fileGroup;
        this.fileGroup.setIncludeSubdirs(true);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean startOperation(Context context, ResultReceiver resultReceiver, Handler handler) {
        String message = "Folder flatten options:\n" + this.toString();
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);

        try {
            File[] files = fileGroup.listFiles(rootDir);
            for (File f : files) {
                if (GenieService.folderOperationStopped()) {
                    message = "Folder flatten operation cancelled";
                    Log.d(TAG, message);
                    onOperationProgress(resultReceiver, message);
                    return false;
                }

                // move (rename) file to root directory
                File newPath = new File(rootDir.getAbsolutePath() + File.separator +
                        f.getName());
                boolean success = GeneralUtil.rename(f, newPath);

                message = success ? "Moved " : "Failed to move ";
                message = message.concat(f.getAbsolutePath() + " to " + newPath.getAbsolutePath());
                Log.d(TAG, message);
                onOperationProgress(resultReceiver, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Folder flatten operation failed to complete\n" + e.toString();
            Log.d(TAG, message);
            onOperationProgress(resultReceiver, message);
            if (resultReceiver == null) onOperationProgress(context, handler, message);
            return false;
        }
        message = "Folder flatten operation completed successfully";
        Log.d(TAG, message);
        onOperationProgress(resultReceiver, message);
        if (resultReceiver == null) onOperationProgress(context, handler, message);
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: 8/3/2021
        return super.toString();
    }
}
