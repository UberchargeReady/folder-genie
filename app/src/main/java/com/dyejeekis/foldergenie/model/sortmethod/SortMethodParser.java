package com.dyejeekis.foldergenie.model.sortmethod;

import java.util.ArrayList;
import java.util.List;

public class SortMethodParser {

    public static final String TAG = "SortMethodParser";

    private static final String PARAMETER_PREFIX = "-";
    private static final String SORT_METHOD_SEPARATOR = ",";

    public static class SortMethodWrapper {
        private SortMethodType sortMethodType;
        private List<String> parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, List<String> parameters) {
            this.sortMethodType = sortMethodType;
            this.parameters = parameters;
        }
    }

    private List<SortMethodWrapper> sortMethods;

    public SortMethodParser(List<SortMethodWrapper> sortMethods) {
        this.sortMethods = sortMethods;
    }

    public SortMethodParser(String text) {
        sortMethods = new ArrayList<>();
        List<SortMethodType> types = parseTypes(text);
        for (SortMethodType type : types) {
            List<String> params = parseParameters(text, type);
            sortMethods.add(new SortMethodWrapper(type, params));
        }
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
        String s = text.replace(" ", "");
        s = s.substring(s.indexOf(type.name)).substring(0, s.indexOf(SORT_METHOD_SEPARATOR));
        int index = 0;
        while (index != -1) {
            index = s.indexOf(PARAMETER_PREFIX);
            s = s.substring(index+1, s.indexOf(PARAMETER_PREFIX, index+1));
            params.add(s);
        }
        return params;
    }
}
