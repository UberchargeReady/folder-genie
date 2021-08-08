package com.dyejeekis.foldergenie.model.operation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.dyejeekis.foldergenie.service.ServiceResultReceiver;
import com.dyejeekis.foldergenie.util.DisplayToast;

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

    public abstract String getTag();

    public abstract boolean startOperation(Context context, ResultReceiver resultReceiver, Handler handler);

    public void onOperationProgress(ResultReceiver resultReceiver, String message) {
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE, message);
            resultReceiver.send(ServiceResultReceiver.CODE_SHOW_PROGRESS, bundle);
        }
    }

    public void onOperationProgress(Context context, Handler handler, String message, int toastLength) {
        if (handler != null) {
            handler.post(new DisplayToast(context, message, toastLength));
        }
    }

    public void onOperationProgress(Context context, Handler handler, String message) {
        onOperationProgress(context, handler, message, Toast.LENGTH_SHORT);
    }

    public void onOperationComplete(ResultReceiver resultReceiver, boolean success) {
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ServiceResultReceiver.KEY_OPERATION_COMPLETED, true);
            bundle.putBoolean(ServiceResultReceiver.KEY_OPERATION_SUCCESS, success);
            resultReceiver.send(ServiceResultReceiver.CODE_OPERATION_COMPLETION, bundle);
        }
    }

}
