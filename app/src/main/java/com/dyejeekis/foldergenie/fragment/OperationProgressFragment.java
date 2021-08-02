package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class OperationProgressFragment extends Fragment implements ServiceResultReceiver.Receiver {

    private FragmentOperationProgressBinding binding;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOperationProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewOperationProgress.setMovementMethod(new ScrollingMovementMethod());

        binding.buttonStopOperation.setOnClickListener(v -> {
            stopOperation();
            getActivity().onBackPressed();
        });

        startOperation();
    }

    private void startOperation() {
        FolderOperation folderOperation = OperationProgressFragmentArgs.fromBundle(getArguments())
                .getFolderOperation();
        ServiceResultReceiver serviceResultReceiver = new ServiceResultReceiver(
                new Handler(Looper.getMainLooper()));
        serviceResultReceiver.setReceiver(this);
        GenieService.enqueueFolderOperation(getActivity(), serviceResultReceiver, folderOperation);
    }

    private void stopOperation() {
        // TODO: 7/27/2021
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == ServiceResultReceiver.CODE_SHOW_PROGRESS) {
            if (resultData != null) {
                String msg = "\n" + resultData.getString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE);
                binding.textViewOperationProgress.append(msg);
            }
        } else if (resultCode == ServiceResultReceiver.CODE_OPERATION_COMPLETION) {
            boolean completed = resultData.getBoolean(ServiceResultReceiver.KEY_OPERATION_COMPLETED);
            if (completed) binding.buttonStopOperation.setText(getString(R.string.action_back));
        }
    }

}