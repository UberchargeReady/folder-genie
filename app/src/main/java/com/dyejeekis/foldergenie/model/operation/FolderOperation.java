package com.dyejeekis.foldergenie.model.operation;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

import java.io.File;
import java.io.Serializable;

public abstract class FolderOperation implements Serializable {

    protected final File rootDir;

    public FolderOperation(File rootDir) {
        this.rootDir = rootDir;
    }

    public File getRootDir() {
        return rootDir;
    }

    public abstract boolean startOperation(ResultReceiver resultReceiver);

    public boolean startOperation() {
        return startOperation(null);
    }

    public void onOperationProgress(ResultReceiver resultReceiver, String progressMessage) {
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE, progressMessage);
            resultReceiver.send(ServiceResultReceiver.CODE_SHOW_PROGRESS, bundle);
        }
    }
}
