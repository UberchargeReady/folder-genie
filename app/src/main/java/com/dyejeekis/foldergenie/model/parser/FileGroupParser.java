package com.dyejeekis.foldergenie.model.parser;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAll;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAlphanum;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDate;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupExtension;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupSize;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupVideo;
import com.dyejeekis.foldergenie.util.AlphanumRange;
import com.dyejeekis.foldergenie.util.ParameterList;
import com.dyejeekis.foldergenie.util.SizeRange;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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

            String paramString = input.substring(start, end);
            if (isValidParam(getParamName(paramString))) {
                paramString = sanitizeParam(paramString);
                params.add(paramString);
            } else throw new InvalidParameterException("Invalid parameter string: " + paramString);

            start = input.indexOf(PARAMETER_PREFIX, end);
        }
        return params;
    }

    protected boolean isValidParam(String param) {
        if (param.equals(PARAMETER_INCLUDE_SUBDIRECTORIES) || super.isValidParam(param))
            return true;
        switch (fileGroupType) {
            case SIZE:
                return param.equals(PARAMETER_MIN) || param.equals(PARAMETER_MAX);
            case ALPHANUMERIC:
                return param.equals(PARAMETER_START) || param.equals(PARAMETER_END);
            case FILE_EXTENSION:
                return param.equals(PARAMETER_SELECT);
            // TODO: 8/1/2021
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
                long minSize = parameters.getLongParamValueSafe(PARAMETER_MIN);
                long maxSize = parameters.getLongParamValueSafe(PARAMETER_MAX);
                fileGroup = new FileGroupSize(new SizeRange(minSize, maxSize), includeSubdirs);
                break;
            case AUDIO:
                // TODO: 6/13/2021
                fileGroup = new FileGroupAudio(includeSubdirs);
                break;
            case IMAGE:
                // TODO: 6/13/2021
                fileGroup = new FileGroupImage(includeSubdirs);
                break;
            case VIDEO:
                // TODO: 6/13/2021
                fileGroup = new FileGroupVideo(includeSubdirs);
                break;
            case DOCUMENT:
                // TODO: 6/13/2021
                fileGroup = new FileGroupDocument(includeSubdirs);
                break;
            case ALPHANUMERIC:
                String start = parameters.getStringParamValueSafe(PARAMETER_START);
                String end = parameters.getStringParamValueSafe(PARAMETER_END);
                fileGroup = new FileGroupAlphanum(new AlphanumRange(start, end), includeSubdirs);
                break;
            case FILE_EXTENSION:
                String paramValue = parameters.getStringParamValueSafe(PARAMETER_SELECT);
                if (paramValue == null)
                    throw new IllegalArgumentException("Error parsing " + PARAMETER_SELECT + " param value");
                String[] strings = paramValue.split(PARAMETER_SELECT_SEPARATOR);
                List<String> extensions = new ArrayList<>();
                for (String s : strings) {
                    extensions.add(s.trim());
                }
                fileGroup = new FileGroupExtension(extensions, includeSubdirs);
                break;
            case DATE_CREATED:
            case DATE_MODIFIED:
            case YEAR_CREATED:
            case YEAR_MODIFIED:
            case MONTH_CREATED:
            case MONTH_MODIFIED:
                // TODO: 6/13/2021
                fileGroup = new FileGroupDate(includeSubdirs);
                break;
            default:
                throw new IllegalArgumentException("Invalid file group type");
        }
        return fileGroup;
    }
}
