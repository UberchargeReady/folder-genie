package com.dyejeekis.foldergenie.fragment.options;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.activity.MainActivity;
import com.dyejeekis.foldergenie.databinding.FragmentSortOptionsVerboseBinding;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.parser.FileGroupParser;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.parser.SortMethodParser;
import com.dyejeekis.foldergenie.util.FileUtil;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.File;
import java.util.List;

public class VerboseSortOptionsFragment extends SortOptionsFragment {

    private FragmentSortOptionsVerboseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSortOptionsVerboseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBrowseDirectories.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
            ((MainActivity) getActivity()).getActivityLauncher().launch(intent, result -> {
                if (result.getData() != null) {
                    Uri treeUri = result.getData().getData();
                    File file = new File(FileUtil.getFullPathFromTreeUri(treeUri, getActivity()));
                    binding.textViewSelectedDir.setText(file.getAbsolutePath());
                }
            });
        });

        binding.buttonCheckSortParameters.setOnClickListener(v -> {
            GeneralUtil.hideKeyboard(getActivity());
            updateFolderSort();
            String sortInfo = getFolderSortInfo();
            binding.textViewSortInfo.setText(sortInfo);
            binding.buttonSaveSortPreset.setEnabled(folderSort != null);
            binding.buttonBeginSort.setEnabled(folderSort != null);
        });

        binding.buttonSaveSortPreset.setEnabled(false);
        binding.buttonSaveSortPreset.setOnClickListener(v -> {
            // TODO: 7/23/2021
        });

        binding.textViewSortInfo.setMovementMethod(new ScrollingMovementMethod());

        binding.buttonBeginSort.setEnabled(false);
        binding.buttonBeginSort.setOnClickListener(v -> {
            startFolderSort();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected File getDirectory() {
        return new File(binding.textViewSelectedDir.getText().toString());
    }

    @Override
    protected FileGroup getFileGroup() {
        FileGroupParser fileGroupParser = new FileGroupParser(binding.editTextFileGroup.getText().toString());
        return fileGroupParser.getFileGroup();
    }

    @Override
    protected List<SortMethod> getSortMethods() {
        SortMethodParser sortMethodParser = new SortMethodParser(binding.editTextSortMethod.getText().toString());
        return sortMethodParser.getSortMethods();
    }

    @Override
    protected boolean validateInputs() {
        // TODO: 7/8/2021
        if (binding.textViewSelectedDir.getText().toString().equals(getString(R.string.label_select_folder)))
            throw new IllegalArgumentException("Please choose a valid directory");
        return true;
    }

    @Override
    protected void highlightInvalidInputs() {
        // TODO: 7/8/2021
    }

}