package com.dyejeekis.foldergenie.fragment.progress;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.View;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.activity.MainActivity;
import com.dyejeekis.foldergenie.fragment.BaseFragment;
import com.dyejeekis.foldergenie.model.operation.FolderOperation;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

public abstract class OperationProgressFragment extends BaseFragment {

    public static final String KEY_FOLDER_OPERATION = "key.FOLDER_OPERATION";
    public static final String KEY_OPERATION_STARTED = "key.OPERATION_STARTED";

    protected abstract void onOperationProgress(int progressMax, int progressCurrent, String message);
    protected abstract void onOperationCompleted(boolean success);

    private OperationProgressViewModel viewModel;
    private boolean operationStarted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            operationStarted = savedInstanceState.getBoolean(KEY_OPERATION_STARTED);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OperationProgressViewModel.class);
        viewModel.getUpdate().observe(getViewLifecycleOwner(), update -> {
            if (update.completed) onOperationCompleted(update.success);
            else onOperationProgress(update.progressMax, update.progressCurrent, update.message);
        });
        startOperation();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_OPERATION_STARTED, operationStarted);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.setGroupVisible(R.id.group_menu_main, false);
        super.onPrepareOptionsMenu(menu);
    }

    private void startOperation() {
        if (!operationStarted) {
            operationStarted = true;
            FolderOperation folderOperation = (FolderOperation) getArguments().getSerializable(KEY_FOLDER_OPERATION);
            ServiceResultReceiver serviceResultReceiver = new ServiceResultReceiver(new Handler(Looper.getMainLooper()));
            serviceResultReceiver.setReceiver(viewModel);
            GenieService.enqueueFolderOperation(getActivity(), serviceResultReceiver, folderOperation);
        }
    }

    protected void stopOperation() {
        GenieService.stopFolderOperation();
    }

}