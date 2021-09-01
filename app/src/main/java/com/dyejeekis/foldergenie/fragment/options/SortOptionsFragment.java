package com.dyejeekis.foldergenie.fragment.options;

import android.os.Bundle;
import android.widget.Toast;

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

    protected FolderSort folderSort;
    protected Exception exception;

    protected abstract File getDirectory();
    protected abstract FileGroup getFileGroup();
    protected abstract List<SortMethod> getSortMethods();
    protected abstract boolean validateInputs();
    protected abstract void highlightInvalidInputs();

    protected String getFolderSortInfo() {
        if (checkPermissions()) {
            if (folderSort == null)
                return "Invalid/incomplete inputs\n\n" + exception.toString();
            else return folderSort.toString();
        }
        return null;
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

    private boolean checkPermissions() {
        if (getActivity() instanceof MainActivity) {
            return ((MainActivity) getActivity()).checkPermissions(false);
        }
        return true;
    }
}
