package com.dyejeekis.foldergenie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class FolderSortService extends JobIntentService {

    public static final String TAG = "FolderSortService";

    public static final int JOB_ID = 1000;

    public static final String ACTION_SORT_FILES = "action.SORT_FILES";
    public static final String ACTION_FLATTEN_FILE_TREE = "action.FLATTEN_FILE_TREE";

    public static final String EXTRA_SELECTED_DIR = "selectedDir";
    public static final String EXTRA_FOLDER_SORT = "folderSort";

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, FolderSortService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork() called with: intent = [" + intent + "]");
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_SORT_FILES:
                    // TODO: 6/6/2021
                    break;
                case ACTION_FLATTEN_FILE_TREE:
                    // TODO: 6/6/2021
                    break;
            }
        }
    }
}
