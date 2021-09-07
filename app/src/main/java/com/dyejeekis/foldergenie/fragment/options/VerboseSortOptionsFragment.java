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
                    selectedDir = file.getAbsolutePath();
                    binding.textViewSelectedDir.setText(selectedDir);
                }
            });
        });

        binding.buttonCheckSortParameters.setOnClickListener(v -> {
            GeneralUtil.hideKeyboard(getActivity());
            if (checkPermissions()) {
                updateFolderSort();
                String sortInfo = getFolderSortInfo();
                binding.textViewSortInfo.setText(sortInfo);
                binding.buttonSaveSortPreset.setEnabled(folderSort != null);
                binding.buttonBeginSort.setEnabled(folderSort != null);
            }
        });

        binding.buttonSaveSortPreset.setEnabled(folderSort != null);
        binding.buttonSaveSortPreset.setOnClickListener(v -> {
            saveSortPreset();
        });

        binding.textViewSortInfo.setMovementMethod(new ScrollingMovementMethod());

        binding.buttonBeginSort.setEnabled(folderSort != null);
        binding.buttonBeginSort.setOnClickListener(v -> {
            startFolderSort();
        });

        if (selectedDir != null)
            binding.textViewSelectedDir.setText(selectedDir);
        else if (folderSort != null)
            binding.textViewSelectedDir.setText(folderSort.getRootDir().getAbsolutePath());
        if (folderSort != null || exception != null)
            binding.textViewSortInfo.setText(getFolderSortInfo());
    }

    private String getFolderSortInfo() {
        if (folderSort == null) {
            String s = "Invalid/incomplete inputs";
            if (exception != null) s = s.concat("\n\n" + exception.toString());
            return s;
        }
        return "Current sort options:\n" + folderSort.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected File getDirectory() {
        selectedDir = binding.textViewSelectedDir.getText().toString();
        if (selectedDir.equals(getString(R.string.label_select_folder))) {
            selectedDir = null;
            return null;
        }
        return new File(selectedDir);
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
        if (binding.textViewSelectedDir.getText().toString().equals(getString(R.string.label_select_folder)))
            throw new IllegalArgumentException("Please choose a valid directory");
        return true;
    }

}