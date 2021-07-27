package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.databinding.FragmentOperationProgressBinding;
import com.dyejeekis.foldergenie.model.operation.FolderOperation;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

public class OperationProgressFragment extends Fragment implements ServiceResultReceiver.Receiver {

    // TODO: 7/27/2021 change stop button to back on operation completion
    // TODO: 7/27/2021 concat operation progress messages during operation

    private FragmentOperationProgressBinding binding;

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

        startOperation();

        binding.buttonStopOperation.setOnClickListener(v -> {
            stopOperation();
            getActivity().onBackPressed();
        });
    }

    private void startOperation() {
        FolderOperation folderOperation = OperationProgressFragmentArgs.fromBundle(getArguments())
                .getFolderOperation();
        //binding.textViewSortProgress.setText(folderSort.toString());
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
                String msg = resultData.getString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE);
                binding.textViewSortProgress.setText(msg);
            }
        }
    }
}