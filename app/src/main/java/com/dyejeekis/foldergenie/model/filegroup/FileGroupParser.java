package com.dyejeekis.foldergenie.model.filegroup;

import java.util.ArrayList;
import java.util.List;

public class FileGroupParser {

    public static final String TAG = "FileGroupParser";

    public static final String PARAMETER_PREFIX = "/";
    // parameters must be lower case strings
    public static final String PARAMETER_INCLUDE_SUBDIRS = "includesubdirs";

    private String input;
    private final FileGroupType fileGroupType;
    private final List<String> parameters;

    public FileGroupParser(FileGroupType fileGroupType, List<String> parameters) {
        this.fileGroupType = fileGroupType;
        this.parameters = parameters;
    }

    public FileGroupParser(String input) {
        this.input = sanitizeInput(input);
        this.fileGroupType = parseType();
        this.parameters = parseParameters();
    }

    public FileGroupType getType() {
        return fileGroupType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    private String sanitizeInput(String input) {
        return input.replace(" ", "").toLowerCase();
    }

    private FileGroupType parseType() {
        for (FileGroupType type : FileGroupType.values()) {
            if (input.contains(type.name.toLowerCase())) return type;
        }
        return null;
    }

    private List<String> parseParameters() {
        List<String> params = new ArrayList<>();
        String s = input;
        int index = s.indexOf(PARAMETER_PREFIX);
        while (index != -1) {
            s = s.substring(index+1, s.indexOf(PARAMETER_PREFIX, index+1));
            params.add(s);
            index = s.indexOf(PARAMETER_PREFIX);
        }
        return params;
    }

    public FileGroup getFileGroup() {
        switch (fileGroupType) {
            case ALL:
                boolean includeSubdirs = parameters.contains(PARAMETER_INCLUDE_SUBDIRS);
                return new FileGroupAll(includeSubdirs);
            case SIZE:
                // TODO: 6/13/2021
                return null;
            case AUDIO:
                // TODO: 6/13/2021
                return null;
            case IMAGE:
                // TODO: 6/13/2021
                return null;
            case VIDEO:
                // TODO: 6/13/2021
                return null;
            case DOCUMENT:
                // TODO: 6/13/2021
                return null;
            case ALPHANUMERIC:
                // TODO: 6/13/2021
                return null;
            case FILE_EXTENSION:
                // TODO: 6/13/2021
                return null;
            case DATE_CREATED:
            case DATE_MODIFIED:
            case YEAR_CREATED:
            case YEAR_MODIFIED:
            case MONTH_CREATED:
            case MONTH_MODIFIED:
                // TODO: 6/13/2021
                return null;
            default:
                throw new IllegalArgumentException("Invalid file group type");
        }
    }

}
