package com.dyejeekis.foldergenie.fragment.progress;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

public class OperationProgressViewModel extends ViewModel implements ServiceResultReceiver.Receiver {

    private MutableLiveData<OperationUpdate> update;

    public LiveData<OperationUpdate> getUpdate() {
        if (update == null) {
            update = new MutableLiveData<>();
        }
        return update;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == ServiceResultReceiver.CODE_SHOW_PROGRESS) {
            if (resultData != null) {
                int max = resultData.getInt(ServiceResultReceiver.KEY_PROGRESS_MAX);
                int current = resultData.getInt(ServiceResultReceiver.KEY_PROGRESS_CURRENT);
                String message = "\n" + resultData.getString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE);
                update.setValue(new OperationUpdate(max, current, message, false, false));
            }
        } else if (resultCode == ServiceResultReceiver.CODE_OPERATION_COMPLETION) {
            boolean completed = resultData.getBoolean(ServiceResultReceiver.KEY_OPERATION_COMPLETED);
            boolean success = resultData.getBoolean(ServiceResultReceiver.KEY_OPERATION_SUCCESS);
            update.setValue(new OperationUpdate(-1, -1, null,
                    completed, success));
        }
    }

    public static class OperationUpdate {

        int progressMax;
        int progressCurrent;
        String message;
        boolean completed;
        boolean success;

        public OperationUpdate(int progressMax, int progressCurrent, String message, boolean completed, boolean success) {
            this.progressMax = progressMax;
            this.progressCurrent = progressCurrent;
            this.message = message;
            this.completed = completed;
            this.success = success;
        }
    }

}
