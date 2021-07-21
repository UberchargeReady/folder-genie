package com.dyejeekis.foldergenie.model.filegroup;

import com.dyejeekis.foldergenie.model.ParameterList;

import java.util.List;

public class FileGroupParser {

    public static final String TAG = "FileGroupParser";

    public static final String PARAMETER_PREFIX = " -";
    // parameters must be lower case strings
    public static final String PARAMETER_INCLUDE_SUBDIRS = "subdirs";
    public static final String PARAMETER_SIZE_MIN = "min";
    public static final String PARAMETER_SIZE_MAX = "max";

    public static final String[] VALID_PARAMETERS =
            {PARAMETER_INCLUDE_SUBDIRS, PARAMETER_SIZE_MIN, PARAMETER_SIZE_MAX};

    private String input;
    private final FileGroupType fileGroupType;
    private final ParameterList parameters;

    public FileGroupParser(FileGroupType fileGroupType, ParameterList parameters) {
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
        return input.toLowerCase();
    }

    private FileGroupType parseType() {
        for (FileGroupType type : FileGroupType.values()) {
            if (input.contains(type.name.toLowerCase())) return type;
        }
        return null;
    }

    private ParameterList parseParameters() {
        ParameterList params = new ParameterList();
        int start = input.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            int end = input.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
            if (end == -1) end = input.length();

            String param = sanitizeParam(input.substring(start, end));
            if (isValidParam(param)) params.add(param);

            start = input.indexOf(PARAMETER_PREFIX, end);
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

    public FileGroup getFileGroup() {
        FileGroup fileGroup;
        final boolean includeSubdirs = parameters.contains(PARAMETER_INCLUDE_SUBDIRS);
        switch (fileGroupType) {
            case ALL:
                fileGroup = new FileGroupAll(includeSubdirs);
                return fileGroup;
            case SIZE:
                // TODO: 6/13/2021
                long minSize = parameters.getLongParamValue(PARAMETER_SIZE_MIN);
                long maxSize = parameters.getLongParamValue(PARAMETER_SIZE_MAX);
                fileGroup = new FileGroupSize(includeSubdirs, minSize, maxSize);
                return fileGroup;
            case AUDIO:
                // TODO: 6/13/2021
                fileGroup = new FileGroupAudio(includeSubdirs);
                return fileGroup;
            case IMAGE:
                // TODO: 6/13/2021
                fileGroup = new FileGroupImage(includeSubdirs);
                return fileGroup;
            case VIDEO:
                // TODO: 6/13/2021
                fileGroup = new FileGroupVideo(includeSubdirs);
                return fileGroup;
            case DOCUMENT:
                // TODO: 6/13/2021
                fileGroup = new FileGroupDocument(includeSubdirs);
                return fileGroup;
            case ALPHANUMERIC:
                // TODO: 6/13/2021
                fileGroup = new FileGroupAlphanum(includeSubdirs);
                return fileGroup;
            case FILE_EXTENSION:
                // TODO: 6/13/2021
                fileGroup = new FileGroupExtension(includeSubdirs);
                return fileGroup;
            case DATE_CREATED:
            case DATE_MODIFIED:
            case YEAR_CREATED:
            case YEAR_MODIFIED:
            case MONTH_CREATED:
            case MONTH_MODIFIED:
                // TODO: 6/13/2021
                fileGroup = new FileGroupDate(includeSubdirs);
                return fileGroup;
            default:
                throw new IllegalArgumentException("Invalid file group type");
        }
    }

}
