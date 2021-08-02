package com.dyejeekis.foldergenie.model.parser;

import java.util.Arrays;
import java.util.List;

public abstract class TextParser {

    // text parser parameters, must be lower case strings

    public static final String PARAMETER_PREFIX = "-";
    public static final String PARAMETER_RANGE_SEPARATOR = "to";
    public static final String PARAMETER_INCLUDE_SUBDIRECTORIES = "subdir";
    public static final String PARAMETER_ADD_TO_ARCHIVE = "archive";
    public static final String PARAMETER_ADD_TO_FILENAME = "rename";
    public static final String PARAMETER_FILES_PER_DIR = "filecount";
    public static final String PARAMETER_RANGE = "range";
    public static final String PARAMETER_MIN = "min";
    public static final String PARAMETER_MAX = "max";
    public static final String PARAMETER_START = "start";
    public static final String PARAMETER_END = "end";

    public static final String[] VALID_PARAMETERS_TEST = {"param1", "param2", "param3", "param4",
            "param5", "param6", "param7", "param8", "param9", "param10", "param11", "param12"};

    protected String input;

    public TextParser(String input) {
        this.input = sanitizeInput(input);
    }

    protected String sanitizeInput(String input) {
        return input.toLowerCase();
    }

    protected String sanitizeParam(String param) {
        return param.replace(" ", "");
    }

    protected String getParamName(String paramString) {
        try {
            String s = paramString.trim();
            if (s.startsWith(PARAMETER_PREFIX)) {
                int end = s.indexOf(" ", 1);
                if (end == -1) end = s.length();
                return s.substring(1, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected boolean isValidParam(String param) {
        return Arrays.asList(VALID_PARAMETERS_TEST).contains(param);
    }

}
