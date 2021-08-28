package com.dyejeekis.foldergenie.model;

import androidx.annotation.NonNull;

import com.dyejeekis.foldergenie.util.GeneralUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public boolean overlapsWith(ExtensionGroup group) {
        for (String s : group) {
            if (this.contains(s)) return true;
        }
        return false;
    }

    public static boolean groupsOverlap(List<ExtensionGroup> groups) {
        for (int i = 0; i < groups.size(); i++) {
            for (int j = i+1; j < groups.size(); j++) {
                if (groups.get(i).overlapsWith(groups.get(j))) return true;
            }
        }
        return false;
    }

    private boolean validateExtensions(List<String> extensions) {
//        for (String extension : extensions) {
//            // TODO: 8/26/2021
//        }
        return true;
    }

    private boolean containsDuplicates(List<String> list) {
        Set<String> set = new HashSet<>(list);
        return set.size() < list.size();
    }

    @NonNull
    @Override
    public String toString() {
        return GeneralUtil.listToString(this, " ");
    }
}
