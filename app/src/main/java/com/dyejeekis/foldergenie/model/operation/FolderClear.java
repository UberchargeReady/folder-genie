package com.dyejeekis.foldergenie.model.operation;

import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;

import java.io.File;

public class FolderClear extends FolderOperation {

    public static final String TAG = FolderClear.class.getSimpleName();

    public FolderClear(File rootDir) {
        super(rootDir);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public boolean startOperation(Context context, ResultReceiver resultReceiver, Handler handler) {
        // TODO: 7/26/2021
        return false;
    }
}
