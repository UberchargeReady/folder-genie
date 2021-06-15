package com.dyejeekis.foldergenie.model.sortmethod;

import java.util.ArrayList;
import java.util.List;

public class SortMethodParser {

    public static final String TAG = "SortMethodParser";

    public static final String SORT_METHOD_SEPARATOR = ",";
    public static final String PARAMETER_PREFIX = "-";
    // parameters must be lower case strings

    public static class SortMethodWrapper {
        private SortMethodType sortMethodType;
        private List<String> parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, List<String> parameters) {
            this.sortMethodType = sortMethodType;
            this.parameters = parameters;
        }
    }

    private String input;
    private List<SortMethodWrapper> sortMethods;

    public SortMethodParser(List<SortMethodWrapper> sortMethods) {
        this.sortMethods = sortMethods;
    }

    public SortMethodParser(String input) {
        this.input = sanitizeInput(input);
        List<SortMethodType> types = parseTypes(this.input);
        sortMethods = new ArrayList<>();
        for (SortMethodType type : types) {
            List<String> params = parseParameters(this.input, type);
            sortMethods.add(new SortMethodWrapper(type, params));
        }
    }

    private String sanitizeInput(String input) {
        return input.replace(" ", "").toLowerCase();
    }

    private List<SortMethodType> parseTypes(String text) {
        List<SortMethodType> types = new ArrayList<>();
        for (SortMethodType type : SortMethodType.values()) {
            if (text.contains(type.name)) types.add(type);
        }
        return types;
    }

    private List<String> parseParameters(String text, SortMethodType type) {
        List<String> params = new ArrayList<>();
        String s = text.substring(text.indexOf(type.name))
                .substring(0, text.indexOf(SORT_METHOD_SEPARATOR));
        int index = 0;
        while (index != -1) {
            index = s.indexOf(PARAMETER_PREFIX);
            s = s.substring(index+1, s.indexOf(PARAMETER_PREFIX, index+1));
            params.add(s);
        }
        return params;
    }
}
