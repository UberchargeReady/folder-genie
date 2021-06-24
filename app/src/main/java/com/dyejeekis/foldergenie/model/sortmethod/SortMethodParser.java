package com.dyejeekis.foldergenie.model.sortmethod;

import java.util.ArrayList;
import java.util.List;

public class SortMethodParser {

    public static final String TAG = "SortMethodParser";

    public static final String SORT_METHOD_SEPARATOR = ",";
    public static final String PARAMETER_PREFIX = "-";
    // parameters must be lower case strings

    public static class SortMethodWrapper {
        public SortMethodType sortMethodType;
        public List<String> parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, List<String> parameters) {
            this.sortMethodType = sortMethodType;
            this.parameters = parameters;
        }
    }

    private String input;
    private List<SortMethodWrapper> sortMethodWrappers;

    public SortMethodParser(List<SortMethodWrapper> sortMethodWrappers) {
        this.sortMethodWrappers = sortMethodWrappers;
    }

    public SortMethodParser(String input) {
        this.input = sanitizeInput(input);
        List<SortMethodType> types = parseTypes();
        sortMethodWrappers = new ArrayList<>();
        for (SortMethodType type : types) {
            List<String> params = parseParameters(type);
            sortMethodWrappers.add(new SortMethodWrapper(type, params));
        }
    }

    public List<SortMethodWrapper> getSortMethodWrappers() {
        return sortMethodWrappers;
    }

    public boolean contains(SortMethodType type) {
        for (SortMethodWrapper wrapper : sortMethodWrappers) {
            if (wrapper.sortMethodType == type) return true;
        }
         return false;
    }

    private String sanitizeInput(String input) {
        return input.toLowerCase();
    }

    private List<SortMethodType> parseTypes() {
        List<SortMethodType> types = new ArrayList<>();
        String[] strings = input.split(SORT_METHOD_SEPARATOR);
        for (String s : strings) {
            for (SortMethodType type : SortMethodType.values()) {
                if (s.contains(type.name.toLowerCase())) {
                    types.add(type);
                    break;
                }
            }
        }
        return types;
    }

    private List<String> parseParameters(SortMethodType type) {
        // separate the part of the string that belongs to the given type
        String s = input.substring(input.indexOf(type.name.toLowerCase()));
        int end = s.indexOf(SORT_METHOD_SEPARATOR);
        if (end == -1) end = s.length();
        s = s.substring(0, end);

        List<String> params = new ArrayList<>();
        int start = s.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            end = s.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
            if (end == -1) end = s.length();

            String param = sanitizeParam(s.substring(start, end));
            if (isValidParam(param)) params.add(param);

            start = s.indexOf(PARAMETER_PREFIX, end);
        }
        return params;
    }

    private String sanitizeParam(String param) {
        return param.replace(" ", "");
    }

    private boolean isValidParam(String param) {
        // TODO: 6/16/2021
        return true;
    }

    public List<SortMethod> getSortMethods() {
        // TODO: 6/16/2021
        return null;
    }
}
