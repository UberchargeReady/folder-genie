package com.dyejeekis.foldergenie.fragment.options;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;

import java.io.File;
import java.util.List;

public class SimpleSortOptionsFragment extends SortOptionsFragment {

    // TODO: 8/7/2021

    @Override
    protected File getDirectory() {
        return null;
    }

    @Override
    protected FileGroup getFileGroup() {
        return null;
    }

    @Override
    protected List<SortMethod> getSortMethods() {
        return null;
    }

    @Override
    protected boolean validateInputs() {
        return false;
    }

}