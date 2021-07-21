package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.databinding.FragmentSortProgressBinding;
import com.dyejeekis.foldergenie.model.FolderSort;
import com.dyejeekis.foldergenie.service.GenieService;
import com.dyejeekis.foldergenie.service.ServiceResultReceiver;

public class SortProgressFragment extends Fragment implements ServiceResultReceiver.Receiver {

    private FragmentSortProgressBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSortProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startSortOperation();

        binding.buttonStopSort.setOnClickListener(v -> {
            // TODO: 6/10/2021
            // stop service
            NavHostFragment.findNavController(this).navigate(R.id.stopOperationAction);
        });
    }

    private void startSortOperation() {
        FolderSort folderSort = SortProgressFragmentArgs.fromBundle(getArguments()).getFolderSort();
        //binding.textViewSortProgress.setText(folderSort.toString());
        ServiceResultReceiver serviceResultReceiver = new ServiceResultReceiver(new Handler());
        serviceResultReceiver.setReceiver(this);
        GenieService.enqueueFolderSort(getActivity(), serviceResultReceiver, folderSort);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case ServiceResultReceiver.CODE_SHOW_PROGRESS:
                if (resultData != null) {
                    String msg = resultData.getString(ServiceResultReceiver.KEY_PROGRESS_MESSAGE);
                    binding.textViewSortProgress.setText(msg);
                }
                break;
        }
    }
}