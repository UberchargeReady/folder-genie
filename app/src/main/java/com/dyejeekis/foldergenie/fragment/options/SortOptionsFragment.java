package com.dyejeekis.foldergenie.fragment.options;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.dyejeekis.foldergenie.R;
import com.dyejeekis.foldergenie.activity.MainActivity;
import com.dyejeekis.foldergenie.fragment.BaseFragment;
import com.dyejeekis.foldergenie.fragment.progress.OperationProgressFragment;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.operation.FolderSort;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;

import java.io.File;
import java.util.List;

public abstract class SortOptionsFragment extends BaseFragment {

    public static final String KEY_FOLDER_SORT = "key.FOLDER_SORT";
    public static final String KEY_EXCEPTION = "key.EXCEPTION";
    public static final String KEY_SELECTED_DIR = "key.SELECTED_DIR";

    protected String selectedDir;
    protected FolderSort folderSort;
    protected Exception exception;

    protected abstract File getDirectory();
    protected abstract FileGroup getFileGroup();
    protected abstract List<SortMethod> getSortMethods();
    protected abstract boolean validateInputs();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            selectedDir = savedInstanceState.getString(KEY_SELECTED_DIR);
            folderSort = (FolderSort) savedInstanceState.getSerializable(KEY_FOLDER_SORT);
            exception = (Exception) savedInstanceState.getSerializable(KEY_EXCEPTION);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_FOLDER_SORT, folderSort);
        outState.putSerializable(KEY_EXCEPTION, exception);
        outState.putSerializable(KEY_SELECTED_DIR, selectedDir);
    }

    protected void updateFolderSort() {
        try {
            if (validateInputs()) {
                folderSort = new FolderSort(getDirectory(), getFileGroup(), getSortMethods());
                exception = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            folderSort = null;
            exception = e;
        }
    }

    protected void startFolderSort() {
        if (checkPermissions()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(OperationProgressFragment.KEY_FOLDER_OPERATION, folderSort);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_beginSortVerbose, bundle);
        }
    }

    protected void saveSortPreset() {
        // TODO: 9/1/2021
        Toast.makeText(getContext(), "TODO", Toast.LENGTH_SHORT).show();
    }

    protected boolean checkPermissions() {
        if (getActivity() instanceof MainActivity) {
            return ((MainActivity) getActivity()).checkPermissions(false);
        }
        return true;
    }
}
