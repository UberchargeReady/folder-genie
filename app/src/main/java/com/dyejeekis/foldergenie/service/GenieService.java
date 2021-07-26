package com.dyejeekis.foldergenie.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.dyejeekis.foldergenie.model.operation.FolderFlatten;
import com.dyejeekis.foldergenie.model.operation.FolderOperation;
import com.dyejeekis.foldergenie.model.operation.FolderSort;
import com.dyejeekis.foldergenie.util.DisplayToast;

public class GenieService extends JobIntentService {

    public static final String TAG = GenieService.class.getSimpleName();

    public static final int JOB_ID = 1000;

    public static final String RECEIVER = "receiver";

    public static final String ACTION_FOLDER_OPERATION = "action.FOLDER_OPERATION";

    public static final String EXTRA_FOLDER_OPERATION = "folderOperationExtra";

    public static void enqueueFolderOperation(Context context, ServiceResultReceiver resultReceiver,
                                              FolderOperation folderOperation) {
        Intent intent = new Intent(context, GenieService.class);
        intent.putExtra(RECEIVER, resultReceiver);
        intent.putExtra(EXTRA_FOLDER_OPERATION, folderOperation);
        // TODO: 7/26/2021
    }

//    public static final String ACTION_GENERATE_TEST_FILES = "action.GENERATE_TEST_FILES";
//    public static final String ACTION_SORT_FILES = "action.SORT_FILES";
//    public static final String ACTION_FLATTEN_FILE_TREE = "action.FLATTEN_FILE_TREE";
//    public static final String ACTION_CLEAR_EMPTY_DIRS = "action.CLEAR_EMPTY_DIRS";
//
//    public static final String EXTRA_FOLDER_SORT = "folderSort";
//    public static final String EXTRA_FOLDER_FLATTEN = "folderFlatten";
//
//    public static void enqueueFolderSort(Context context, ServiceResultReceiver
//                                   workerResultReceiver, FolderSort folderSort) {
//        Intent intent = new Intent(context, GenieService.class);
//        intent.putExtra(RECEIVER, workerResultReceiver);
//        intent.putExtra(EXTRA_FOLDER_SORT, folderSort);
//        intent.setAction(ACTION_SORT_FILES);
//        enqueueWork(context, GenieService.class, JOB_ID, intent);
//    }
//
//    public static void enqueueFolderFlatten(Context context, ServiceResultReceiver
//                                            workerResultReceiver, FolderFlatten folderFlatten) {
//        Intent intent = new Intent(context, GenieService.class);
//        intent.putExtra(RECEIVER, workerResultReceiver);
//        intent.putExtra(EXTRA_FOLDER_FLATTEN, folderFlatten);
//        intent.setAction(ACTION_FLATTEN_FILE_TREE);
//        enqueueWork(context, GenieService.class, JOB_ID, intent);
//    }

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent + "]");
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_FOLDER_OPERATION:
                    ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);
                    FolderOperation folderOp = (FolderOperation) intent.getSerializableExtra(EXTRA_FOLDER_OPERATION);
                    // TODO: 7/26/2021
                    break;
            }
//            ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);
//            boolean success = false;
//            switch (intent.getAction()) {
//                case ACTION_GENERATE_TEST_FILES:
//                    break;
//                case ACTION_SORT_FILES:
//                    FolderSort folderSort = (FolderSort) intent.getSerializableExtra(EXTRA_FOLDER_SORT);
//                    success = folderSort.startOperation(resultReceiver);
//                    break;
//                case ACTION_FLATTEN_FILE_TREE:
//                    FolderFlatten folderFlatten = (FolderFlatten) intent.getSerializableExtra(EXTRA_FOLDER_FLATTEN);
//                    success = folderFlatten.startOperation(resultReceiver);
//                    break;
//                case ACTION_CLEAR_EMPTY_DIRS:
//                    break;
//            }
//            if (resultReceiver == null) {
//                if (success) {
//                    handler.post(new DisplayToast(this, "Operation (" + intent.getAction() + ") completed successfully",
//                            Toast.LENGTH_SHORT));
//                } else {
//                    handler.post(new DisplayToast(this, "Failed to complete operation (" + intent.getAction() + ")",
//                            Toast.LENGTH_SHORT));
//                }
//            }
        }
    }

}
