package com.dyejeekis.foldergenie.model.operation;

import android.os.ResultReceiver;
import android.util.Log;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAll;
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
    public boolean startOperation(ResultReceiver resultReceiver) {
        try {
            File[] files = fileGroup.listFiles(rootDir);
            for (File f : files) {
                // move (rename) file to root directory
                File newPath = new File(rootDir.getAbsolutePath() + File.separator +
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

}
