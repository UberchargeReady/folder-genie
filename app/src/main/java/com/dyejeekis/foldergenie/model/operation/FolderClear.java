package com.dyejeekis.foldergenie.model.operation;

import android.os.ResultReceiver;

import java.io.File;

public class FolderClear extends FolderOperation {

    public static final String TAG = FolderClear.class.getSimpleName();

    public FolderClear(File rootDir) {
        super(rootDir);
    }

    @Override
    public boolean startOperation(ResultReceiver resultReceiver) {
        // TODO: 7/26/2021
        return false;
    }
}
