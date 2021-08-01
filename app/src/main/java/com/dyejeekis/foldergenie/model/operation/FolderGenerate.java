package com.dyejeekis.foldergenie.model.operation;

import android.os.ResultReceiver;

import java.io.File;

public class FolderGenerate extends FolderOperation {

    public static final String TAG = FolderGenerate.class.getSimpleName();

    public FolderGenerate(File rootDir) {
        super(rootDir);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean startOperation(ResultReceiver resultReceiver) {
        // TODO: 7/26/2021
        return false;
    }
}
