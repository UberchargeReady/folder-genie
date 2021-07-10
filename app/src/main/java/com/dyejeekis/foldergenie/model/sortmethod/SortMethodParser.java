package com.dyejeekis.foldergenie.model.sortmethod;

import com.dyejeekis.foldergenie.util.ParameterList;

import java.util.ArrayList;
import java.util.List;

public class SortMethodParser {

    public static final String TAG = "SortMethodParser";

    public static final String SORT_METHOD_SEPARATOR = ",";
    public static final String PARAMETER_PREFIX = "-";
    // parameters must be lower case strings
    public static final String PARAMETER_ADD_TO_ARCHIVE = "archive";
    public static final String PARAMETER_ADD_TO_FILENAME = "rename";
    public static final String PARAMETER_FILES_PER_DIR = "filecount";

    public static final String[] VALID_PARAMETERS =
            {};

    public static class SortMethodWrapper {
        public SortMethodType sortMethodType;
        public ParameterList parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, ParameterList parameters) {
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
            ParameterList params = parseParameters(type);
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

    private ParameterList parseParameters(SortMethodType type) {
        // separate the part of the string that belongs to the given type
        String s = input.substring(input.indexOf(type.name.toLowerCase()));
        int end = s.indexOf(SORT_METHOD_SEPARATOR);
        if (end == -1) end = s.length();
        s = s.substring(0, end);

        ParameterList params = new ParameterList();
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
        List<SortMethod> sortMethods = new ArrayList<>();
        for (SortMethodWrapper sortMethodWrapper : sortMethodWrappers) {
            SortMethod sortMethod;
            ParameterList params = sortMethodWrapper.parameters;
            final boolean addToArchive = params.contains(PARAMETER_ADD_TO_ARCHIVE);
            final boolean addToFilename = params.contains(PARAMETER_ADD_TO_FILENAME);
            switch (sortMethodWrapper.sortMethodType) {
                case SPLIT:
                    int filecount = params.getIntParamValue(PARAMETER_FILES_PER_DIR);
                    sortMethod = new SortMethodSplit(filecount, addToArchive, addToFilename);
                    break;
                case SIZE:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodSize(addToArchive, addToFilename);
                    break;
                case FILE_EXTENSION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodExtension(addToArchive, addToFilename);
                    break;
                case ALPHANUMERIC:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodAlphanum(addToArchive, addToFilename);
                    break;
                case IMAGE_RESOLUTION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodImageRes(addToArchive, addToFilename);
                    break;
                case DATE_CREATED:
                case YEAR_CREATED:
                case DATE_MODIFIED:
                case MONTH_CREATED:
                case YEAR_MODIFIED:
                case MONTH_MODIFIED:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodDate(addToArchive, addToFilename);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort method type");
            }
            sortMethods.add(sortMethod);
        }
        return sortMethods;
    }
}
