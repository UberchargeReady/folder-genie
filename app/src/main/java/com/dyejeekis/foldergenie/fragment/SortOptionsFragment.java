package com.dyejeekis.foldergenie.fragment;

import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;

import com.dyejeekis.foldergenie.R;
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
        if (folderSort == null)
            return "Invalid/incomplete inputs\n\n" + exception.toString();
        else return folderSort.toString();
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
        Bundle bundle = new Bundle();
        bundle.putSerializable(OperationProgressFragment.KEY_FOLDER_OPERATION, folderSort);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_beginSortVerbose, bundle);
    }
}
