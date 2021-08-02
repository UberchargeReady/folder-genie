package com.dyejeekis.foldergenie.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.dyejeekis.foldergenie.model.operation.FolderOperation;
import com.dyejeekis.foldergenie.util.DisplayToast;

public class GenieService extends JobIntentService {

    public static final String TAG = GenieService.class.getSimpleName();

    public static final int JOB_ID = 1000;

    public static final String ACTION_FOLDER_OPERATION = "action.FOLDER_OPERATION";

    public static final String EXTRA_RESULT_RECEIVER = "extra.RESULT_RECEIVER";
    public static final String EXTRA_FOLDER_OPERATION = "extra.FOLDER_OPERATION";

    public static void enqueueFolderOperation(Context context, ServiceResultReceiver resultReceiver,
                                              FolderOperation folderOperation) {
        Intent intent = new Intent(context, GenieService.class);
        intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
        intent.putExtra(EXTRA_FOLDER_OPERATION, folderOperation);
        intent.setAction(ACTION_FOLDER_OPERATION);
        enqueueWork(context, GenieService.class, JOB_ID, intent);
    }

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent + "]");
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_FOLDER_OPERATION:
                    ResultReceiver resultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                    FolderOperation folderOperation = (FolderOperation) intent
                            .getSerializableExtra(EXTRA_FOLDER_OPERATION);
                    boolean success = folderOperation.startOperation(resultReceiver);
                    if (resultReceiver == null) {
                        String opName = folderOperation.getTag();
                        String message = success ? "Operation (" + opName + ") completed successfully"
                                : "Failed to complete operation (" + opName + ")";
                        handler.post(new DisplayToast(this, message, Toast.LENGTH_SHORT));
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(ServiceResultReceiver.KEY_OPERATION_COMPLETED, true);
                        resultReceiver.send(ServiceResultReceiver.CODE_OPERATION_COMPLETION, bundle);
                    }
                    break;
            }
        }
    }

}
