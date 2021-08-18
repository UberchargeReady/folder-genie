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
import java.util.Arrays;
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
        if (param.equals(PARAMETER_INCLUDE_SUBDIRECTORIES) || super.isValidParam(param))
            return true;
        switch (fileGroupType) {
            case SIZE:
                return param.equals(PARAMETER_MIN) || param.equals(PARAMETER_MAX);
            case ALPHANUMERIC:
                return param.equals(PARAMETER_START) || param.equals(PARAMETER_END);
            case FILE_EXTENSION:
                return param.equals(PARAMETER_SELECT) || param.equals(PARAMETER_AUDIO)
                        || param.equals(PARAMETER_VIDEO) || param.equals(PARAMETER_IMAGE)
                        || param.equals(PARAMETER_DOCUMENT);
            case DATE:
                return param.equals(PARAMETER_DATE_CREATED) || param.equals(PARAMETER_DATE_MODIFIED)
                        || param.equals(PARAMETER_YEAR_CREATED) || param.equals(PARAMETER_YEAR_MODIFIED)
                        || param.equals(PARAMETER_MONTH_CREATED) || param.equals(PARAMETER_MONTH_MODIFIED)
                        || param.equals(PARAMETER_RANGE_CREATED) || param.equals(PARAMETER_RANGE_MODIFIED)
                        || param.equals(PARAMETER_MIN_CREATED) || param.equals(PARAMETER_MIN_MODIFIED)
                        || param.equals(PARAMETER_MAX_CREATED) || param.equals(PARAMETER_MAX_MODIFIED);
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
                List<String> extensions = new ArrayList<>();
                for (int i=0; i<parameters.size(); i++) {
                    String paramName = getParamName(parameters.get(i));
                    switch (paramName) {
                        case PARAMETER_SELECT:
                            String paramValue = parameters.getStringParamValueSafe(PARAMETER_SELECT, i);
                            if (paramValue == null)
                                throw new IllegalArgumentException("Error parsing " + PARAMETER_SELECT + " param value");
                            String[] strings = paramValue.split(PARAMETER_SELECT_SEPARATOR);
                            for (String s : strings) {
                                extensions.add(s.trim());
                            }
                            break;
                        case PARAMETER_AUDIO:
                            extensions.addAll(Arrays.asList(FileGroupAudio.AUDIO_EXTENSIONS));
                            break;
                        case PARAMETER_VIDEO:
                            extensions.addAll(Arrays.asList(FileGroupVideo.VIDEO_EXTENSIONS));
                            break;
                        case PARAMETER_IMAGE:
                            extensions.addAll(Arrays.asList(FileGroupImage.IMAGE_EXTENSIONS));
                            break;
                        case PARAMETER_DOCUMENT:
                            extensions.addAll(Arrays.asList(FileGroupDocument.DOCUMENT_EXTENSIONS));
                            break;
                    }
                }
                fileGroup = new FileGroupExtension(extensions, includeSubdirs);
                break;
            case DATE:
                // TODO: 6/13/2021
                fileGroup = new FileGroupDate(includeSubdirs);
                break;
            default:
                throw new IllegalArgumentException("Invalid file group type");
        }
        return fileGroup;
    }
}
