package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.model.sortmethod.SortMethodExtension;
import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExtensionGroup extends ArrayList<String> implements Serializable {

    public ExtensionGroup(@NonNull List<String> extensions) {
        if (extensions.isEmpty())
            throw new IllegalArgumentException("Extension group can't be empty");
        if (!validateExtensions(extensions))
            throw new IllegalArgumentException("Extension group (" + toString()
                    + ") contains invalid extension(s)");
        if (containsDuplicates(extensions))
            throw new IllegalArgumentException("Extension group contains duplicate extensions");
        this.addAll(extensions);
    }

    public boolean overlapsWith(ExtensionGroup extensionGroup) {
        // TODO: 8/9/2021
        return false;
    }

    public static boolean groupsOverlap(List<ExtensionGroup> groups) {
        // TODO: 8/25/2021
        return false;
    }

    private boolean validateExtensions(List<String> extensions) {
//        for (String extension : extensions) {
//            // TODO: 8/26/2021
//        }
        return true;
    }

    private boolean containsDuplicates(List<String> extensions) {
        // TODO: 8/8/2021
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return GeneralUtil.listToString(this, " ");
    }
}
