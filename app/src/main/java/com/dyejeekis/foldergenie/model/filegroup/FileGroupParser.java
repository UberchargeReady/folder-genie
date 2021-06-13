package com.dyejeekis.foldergenie.model.filegroup;

import java.util.ArrayList;
import java.util.List;

public class FileGroupParser {

    public static final String TAG = "FileGroupParser";

    private static final String PARAMETER_PREFIX = "-";

    private FileGroupType fileGroupType;
    private List<String> parameters;

    public FileGroupParser(FileGroupType fileGroupType, List<String> parameters) {
        this.fileGroupType = fileGroupType;
        this.parameters = parameters;
    }

    public FileGroupParser(String text) {
        this.fileGroupType = parseType(text);
        this.parameters = parseParameters(text);
    }

    private FileGroupType parseType(String text) {
        for (FileGroupType type : FileGroupType.values()) {
            if (text.contains(type.name)) return type;
        }
        return null;
    }

    private List<String> parseParameters(String text) {
        List<String> params = new ArrayList<>();
        String s = text.replace(" ", "");
        int index = 0;
        while (index != -1) {
            index = s.indexOf(PARAMETER_PREFIX);
            s = s.substring(index+1, s.indexOf(PARAMETER_PREFIX, index+1));
            params.add(s);
        }
        return params;
    }

    public FileGroup getFileGroup() {
        switch (fileGroupType) {
            case ALL:
                // TODO: 6/13/2021
                return null;
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
