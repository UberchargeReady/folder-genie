package com.dyejeekis.foldergenie.parser;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAll;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupName;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDate;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupExtension;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupSize;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;
import com.dyejeekis.foldergenie.model.filegroup.extension.FileGroupVideo;

import java.util.List;

public class FileGroupParser extends TextParser {

    public static final String TAG = FileGroupParser.class.getSimpleName();

    private final FileGroupType fileGroupType;
    private final ParameterList parameters;

    public FileGroupParser(String input) {
        super(input);
        this.fileGroupType = parseType();
        this.parameters = parseParameters();
    }

    public FileGroupType getType() {
        return fileGroupType;
    }

    public List<String> getParameters() {
        return parameters;
    }

    private FileGroupType parseType() {
        for (FileGroupType type : FileGroupType.values()) {
            String typeStr = input.split(PARAMETER_PREFIX)[0].trim().toLowerCase();
            if (typeStr.equals(type.name.toLowerCase())) return type;
        }
        throw new IllegalArgumentException("Invalid file group type");
    }

    private ParameterList parseParameters() {
        ParameterList params = new ParameterList();
        params.setUniqueParams(UNIQUE_PARAMETERS);
        int start = input.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            int end = input.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
            if (end == -1) end = input.length();

            String paramString = input.substring(start, end);
            if (isValidParam(getParamName(paramString))) {
                paramString = sanitizeParamString(paramString);
                params.add(paramString);
            } else throw new InvalidParameterException("Invalid parameter string: " + paramString);

            start = input.indexOf(PARAMETER_PREFIX, end);
        }
        return params;
    }

    protected boolean isValidParam(String param) {
        if (param.equals(PARAMETER_INCLUDE_SUBDIRECTORIES))
            return true;
        switch (fileGroupType) {
            case SIZE:
                return param.equals(PARAMETER_RANGE) || param.equals(PARAMETER_MIN)
                        || param.equals(PARAMETER_MAX);
            case NAME:
                return param.equals(PARAMETER_FROM) || param.equals(PARAMETER_TO)
                        || param.equals(PARAMETER_RANGE) || param.equals(PARAMETER_CASE_SENSITIVE);
            case EXTENSION:
                return param.equals(PARAMETER_GROUP) || param.equals(PARAMETER_SELECT)
                        || param.equals(PARAMETER_AUDIO) || param.equals(PARAMETER_VIDEO)
                        || param.equals(PARAMETER_IMAGE) || param.equals(PARAMETER_DOCUMENT);
            case DATE:
                return param.equals(PARAMETER_DATE) || param.equals(PARAMETER_DATE_MODIFIED)
                        || param.equals(PARAMETER_YEAR) || param.equals(PARAMETER_YEAR_MODIFIED)
                        || param.equals(PARAMETER_MONTH) || param.equals(PARAMETER_MONTH_MODIFIED)
                        || param.equals(PARAMETER_RANGE) || param.equals(PARAMETER_RANGE_MODIFIED)
                        || param.equals(PARAMETER_FROM) || param.equals(PARAMETER_MIN_MODIFIED)
                        || param.equals(PARAMETER_TO) || param.equals(PARAMETER_MAX_MODIFIED);
        }
        return false;
    }

    public FileGroup getFileGroup() {
        FileGroup fileGroup;
        final boolean includeSubdirs = parameters.contains(PARAMETER_INCLUDE_SUBDIRECTORIES);
        switch (fileGroupType) {
            case ALL:
                fileGroup = new FileGroupAll(includeSubdirs);
                break;
            case SIZE:
                fileGroup = new FileGroupSize(parseSizeRanges(parameters), includeSubdirs);
                break;
            case AUDIO:
                fileGroup = new FileGroupAudio(includeSubdirs);
                break;
            case IMAGE:
                fileGroup = new FileGroupImage(includeSubdirs);
                break;
            case VIDEO:
                fileGroup = new FileGroupVideo(includeSubdirs);
                break;
            case DOCUMENT:
                fileGroup = new FileGroupDocument(includeSubdirs);
                break;
            case NAME:
                fileGroup = new FileGroupName(parseAlphanumRanges(parameters), includeSubdirs);
                break;
            case EXTENSION:
                fileGroup = new FileGroupExtension(parseExtensionGroups(parameters), includeSubdirs);
                break;
            case DATE:
                fileGroup = new FileGroupDate(parseDateRanges(parameters), includeSubdirs);
                break;
            default:
                throw new IllegalArgumentException("Invalid file group type");
        }
        return fileGroup;
    }
}
