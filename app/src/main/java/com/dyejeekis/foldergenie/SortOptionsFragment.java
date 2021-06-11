package com.dyejeekis.foldergenie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.databinding.FragmentSortOptionsBinding;
import com.dyejeekis.foldergenie.model.FolderSort;

public class SortOptionsFragment extends Fragment {

    private FragmentSortOptionsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSortOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBrowseDirectories.setOnClickListener(v -> {
            // TODO: 6/6/2021
        });

        binding.buttonCheckSortParameters.setOnClickListener(v -> {
            // TODO: 6/6/2021
        });

        binding.buttonBeginSort.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_SortOptionsFragment_to_SortProgressFragment);
            // TODO: 6/6/2021
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private FolderSort getFolderSort() {
        // TODO: 6/6/2021
        return null;
    }
}