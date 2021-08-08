package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.databinding.FragmentOperationProgressBinding;
import com.dyejeekis.foldergenie.model.operation.FolderOperation;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

public abstract class OperationProgressFragment extends BaseFragment implements ServiceResultReceiver.Receiver {

    public static final String KEY_FOLDER_OPERATION = "key.FOLDER_OPERATION";

    protected abstract void onOperationProgress(String message);
    protected abstract void onOperationCompleted(boolean success);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.setGroupVisible(R.id.group_menu_main, false);
        super.onPrepareOptionsMenu(menu);
    }

    protected void startOperation() {
        FolderOperation folderOperation = (FolderOperation) getArguments().getSerializable(KEY_FOLDER_OPERATION);
        ServiceResultReceiver serviceResultReceiver = new ServiceResultReceiver(
                new Handler(Looper.getMainLooper()));
        serviceResultReceiver.setReceiver(this);
        GenieService.enqueueFolderOperation(getActivity(), serviceResultReceiver, folderOperation);
    }

    protected void stopOperation() {
        // TODO: 7/27/2021
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == ServiceResultReceiver.CODE_SHOW_PROGRESS) {
            if (resultData != null) {
                String msg = "\n" + resultData.getString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE);
                onOperationProgress(msg);
            }
        } else if (resultCode == ServiceResultReceiver.CODE_OPERATION_COMPLETION) {
            boolean completed = resultData.getBoolean(ServiceResultReceiver.KEY_OPERATION_COMPLETED);
            boolean success = resultData.getBoolean(ServiceResultReceiver.KEY_OPERATION_SUCCESS);
            if (completed) onOperationCompleted(success);
        }
    }

}