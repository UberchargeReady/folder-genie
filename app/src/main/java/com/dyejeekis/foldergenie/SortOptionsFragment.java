package com.dyejeekis.foldergenie;

import android.content.Intent;
import android.net.Uri;
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
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupParser;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodParser;
import com.dyejeekis.foldergenie.util.FileUtil;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.List;

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
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
            ((MainActivity) getActivity()).activityLauncher.launch(intent, result -> {
                if (result.getData() != null) {
                    Uri treeUri = result.getData().getData();
                    File file = new File(FileUtil.getFullPathFromTreeUri(treeUri, getActivity()));
                    binding.textViewSelectedDir.setText(file.getAbsolutePath());
                }
            });
        });

        binding.buttonCheckSortParameters.setOnClickListener(v -> {
            GeneralUtil.hideKeyboard(getActivity());
            binding.textViewSortInfo.setText(getFolderSortInfo());
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

    private String getFolderSortInfo() {
        FolderSort folderSort = getFolderSort();
        if (folderSort == null) {
            return "Invalid/incomplete inputs";
        } else return folderSort.toString();
    }

    private FolderSort getFolderSort() {
        try {
            if (validateInputs()) {
                File rootDir = new File(binding.textViewSelectedDir.getText().toString());

                FileGroupParser fileGroupParser = new FileGroupParser(binding.editTextFileGroup.getText().toString());
                FileGroup fileGroup = fileGroupParser.getFileGroup();

                SortMethodParser sortMethodParser = new SortMethodParser(binding.editTextSortMethod.getText().toString());
                List<SortMethod> sortMethods = sortMethodParser.getSortMethods();

                return new FolderSort.Builder()
                        .rootDir(rootDir)
                        .fileGroup(fileGroup)
                        .addSortMethods(sortMethods)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateInputs() {
        // TODO: 7/8/2021
        return true;
    }

    private void highlightInvalidInputs() {
        // TODO: 7/8/2021
    }

}