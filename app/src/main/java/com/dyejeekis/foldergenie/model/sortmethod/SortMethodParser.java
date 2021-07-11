package com.dyejeekis.foldergenie.model.sortmethod;

import com.dyejeekis.foldergenie.util.GeneralUtil;
import com.dyejeekis.foldergenie.util.ParameterList;

import java.util.ArrayList;
import java.util.List;

public class SortMethodParser {

    public static final String TAG = "SortMethodParser";

    public static final String SORT_METHOD_SEPARATOR = ",";
    public static final String PARAMETER_PREFIX = "-";
    public static final String PARAMETER_VALUE_RANGE_SEPARATOR = "to";
    // parameters must be lower case strings
    public static final String PARAMETER_ADD_TO_ARCHIVE = "archive";
    public static final String PARAMETER_ADD_TO_FILENAME = "rename";
    public static final String PARAMETER_FILES_PER_DIR = "filecount";
    public static final String PARAMETER_SIZE_RANGE = "range";

    public static final String[] VALID_PARAMETERS =
            {PARAMETER_ADD_TO_ARCHIVE, PARAMETER_ADD_TO_FILENAME, PARAMETER_FILES_PER_DIR};

    public static class SortMethodWrapper {
        public SortMethodType sortMethodType;
        public ParameterList parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, ParameterList parameters) {
            this.sortMethodType = sortMethodType;
            this.parameters = parameters;
        }
    }

    private String input;
    private final List<SortMethodWrapper> sortMethodWrappers;

    public SortMethodParser(List<SortMethodWrapper> sortMethodWrappers) {
        this.sortMethodWrappers = sortMethodWrappers;
    }

    public SortMethodParser(String input) {
        this.input = sanitizeInput(input);
        this.sortMethodWrappers = parseSortMethodWrappers();
    }

    public List<SortMethodWrapper> getSortMethodWrappers() {
        return sortMethodWrappers;
    }

    private String sanitizeInput(String input) {
        return input.toLowerCase();
    }

    private List<SortMethodWrapper> parseSortMethodWrappers() {
        List<SortMethodWrapper> wrappers = new ArrayList<>();
        String[] strings = input.split(SORT_METHOD_SEPARATOR);
        for (String s : strings) {
            for (SortMethodType type : SortMethodType.values()) {
                if (s.contains(type.name.toLowerCase())) {
                    wrappers.add(new SortMethodWrapper(type, parseParameters(s)));
                    break;
                }
            }
        }
        return wrappers;
    }

    private ParameterList parseParameters(String s) {
        ParameterList params = new ParameterList();
        int start = s.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            int end = s.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
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
                    sortMethod = new SortMethodSize(addToArchive, addToFilename);
                    for (int i=0; i<params.size(); i++) {
                        if (params.get(i).contains(PARAMETER_SIZE_RANGE)) {
                            String range = params.getStringParamValue(PARAMETER_SIZE_RANGE, i);
                            long min = Long.parseLong(range.split(PARAMETER_VALUE_RANGE_SEPARATOR)[0]);
                            long max = Long.parseLong(range.split(PARAMETER_VALUE_RANGE_SEPARATOR)[1]);
                            ((SortMethodSize) sortMethod).addSizeRange(min, max);
                        }
                    }
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
