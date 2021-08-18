package com.dyejeekis.foldergenie.model.parser;

import java.util.Arrays;

public abstract class TextParser {

    // text parser parameters, must be lower case strings

    public static final String COMMAND_SEPARATOR = ";";
    public static final String PARAMETER_PREFIX = "-";
    public static final String PARAMETER_INCLUDE_SUBDIRECTORIES = "subdir";
    public static final String PARAMETER_ADD_TO_ARCHIVE = "archive";
    public static final String PARAMETER_ADD_TO_FILENAME = "rename";
    public static final String PARAMETER_FILES_PER_DIR = "filecount";
    public static final String PARAMETER_SELECT = "select";
    public static final String PARAMETER_SELECT_SEPARATOR = ",";
    public static final String PARAMETER_RANGE = "range";
    public static final String PARAMETER_RANGE_SEPARATOR = "to";
    public static final String PARAMETER_FOLDER = "folder";
    public static final String PARAMETER_FOLDER_SEPARATOR = ",";
    public static final String PARAMETER_GROUP = "group";
    public static final String PARAMETER_GROUP_SEPARATOR = ",";
    public static final String PARAMETER_MIN = "min";
    public static final String PARAMETER_MAX = "max";
    public static final String PARAMETER_START = "start";
    public static final String PARAMETER_END = "end";
    public static final String PARAMETER_AUDIO = "audio";
    public static final String PARAMETER_VIDEO = "videos";
    public static final String PARAMETER_IMAGE = "images";
    public static final String PARAMETER_DOCUMENT = "documents";
    public static final String PARAMETER_DATE_CREATED = "dateCreated";
    public static final String PARAMETER_DATE_MODIFIED = "dateModified";
    public static final String PARAMETER_YEAR_CREATED = "yearCreated";
    public static final String PARAMETER_YEAR_MODIFIED = "yearModified";
    public static final String PARAMETER_MONTH_CREATED = "monthCreated";
    public static final String PARAMETER_MONTH_MODIFIED = "monthModified";
    public static final String PARAMETER_RANGE_CREATED = "rangeCreated";
    public static final String PARAMETER_RANGE_MODIFIED = "rangeModified";
    public static final String PARAMETER_MIN_CREATED = "minCreated";
    public static final String PARAMETER_MIN_MODIFIED = "minModified";
    public static final String PARAMETER_MAX_CREATED = "maxCreated";
    public static final String PARAMETER_MAX_MODIFIED = "maxModified";

    public static final String[] VALID_PARAMETERS_TEST = {"param1", "param2", "param3", "param4",
            "param5", "param6", "param7", "param8", "param9", "param10", "param11", "param12"};

    public static final String[] UNIQUE_PARAMETERS = {PARAMETER_MIN, PARAMETER_MAX, PARAMETER_START,
            PARAMETER_END, PARAMETER_AUDIO, PARAMETER_VIDEO, PARAMETER_IMAGE, PARAMETER_DOCUMENT,
            PARAMETER_MIN_CREATED, PARAMETER_MIN_MODIFIED, PARAMETER_MAX_CREATED, PARAMETER_MAX_MODIFIED};

    protected String input;

    public TextParser(String input) {
        this.input = sanitizeInput(input);
    }

    protected String sanitizeInput(String input) {
        return input.toLowerCase();
    }

    protected String sanitizeParamString(String paramString) {
        // TODO: 8/12/2021
        return paramString;
    }

    public static String getParamName(String paramString) {
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

    // TODO: 8/8/2021 rewrite unit tests and remove/disable this method
    protected boolean isValidParam(String param) {
        return Arrays.asList(VALID_PARAMETERS_TEST).contains(param);
    }

}
