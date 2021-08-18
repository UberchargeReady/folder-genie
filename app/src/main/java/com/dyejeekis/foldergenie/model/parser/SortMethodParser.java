package com.dyejeekis.foldergenie.model.parser;

import com.dyejeekis.foldergenie.model.filegroup.FileGroupAudio;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupDocument;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupImage;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupVideo;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodAlphanum;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodDate;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodExtension;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodImageRes;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodSize;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodSplit;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.util.AlphanumRange;
import com.dyejeekis.foldergenie.util.ParameterList;
import com.dyejeekis.foldergenie.util.SizeRange;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortMethodParser extends TextParser {

    public static final String TAG = SortMethodParser.class.getSimpleName();

    public static final String SORT_METHOD_SEPARATOR = COMMAND_SEPARATOR;

    public static class SortMethodWrapper {
        public SortMethodType sortMethodType;
        public ParameterList parameters;

        public SortMethodWrapper(SortMethodType sortMethodType, ParameterList parameters) {
            this.sortMethodType = sortMethodType;
            this.parameters = parameters;
        }
    }

    private final List<SortMethodWrapper> sortMethodWrappers;

    public SortMethodParser(String input) {
        super(input);
        this.sortMethodWrappers = parseSortMethodWrappers();
    }

    public List<SortMethodWrapper> getSortMethodWrappers() {
        return sortMethodWrappers;
    }

    private List<SortMethodWrapper> parseSortMethodWrappers() {
        List<SortMethodWrapper> wrappers = new ArrayList<>();
        String[] strings = input.split(SORT_METHOD_SEPARATOR);
        for (String s : strings) {
            for (SortMethodType type : SortMethodType.values()) {
                if (s.contains(type.name.toLowerCase())) {
                    wrappers.add(new SortMethodWrapper(type, parseParameters(s, type)));
                    break;
                }
            }
        }
        return wrappers;
    }

    private ParameterList parseParameters(String s, SortMethodType type) {
        ParameterList params = new ParameterList();
        params.setUniqueParams(UNIQUE_PARAMETERS);
        int start = s.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            int end = s.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
            if (end == -1) end = s.length();

            String paramString = s.substring(start, end);
            if (isValidParam(getParamName(paramString), type)) {
                paramString = sanitizeParamString(paramString);
                params.add(paramString);
            } else throw new InvalidParameterException("Invalid parameter string: " + paramString);

            start = s.indexOf(PARAMETER_PREFIX, end);
        }
        return params;
    }

    private boolean isValidParam(String param, SortMethodType type) {
        if (param.equals(PARAMETER_ADD_TO_ARCHIVE) || param.equals(PARAMETER_ADD_TO_FILENAME)
        || super.isValidParam(param)) return true;
        switch (type) {
            case SPLIT:
                return param.equals(PARAMETER_FILES_PER_DIR);
            case SIZE:
                return param.equals(PARAMETER_RANGE) || param.equals(PARAMETER_MIN)
                        || param.equals(PARAMETER_MAX);
            case ALPHANUMERIC:
                return param.equals(PARAMETER_RANGE) || param.equals(PARAMETER_START)
                        || param.equals(PARAMETER_END);
            case FILE_EXTENSION:
                return param.equals(PARAMETER_GROUP) || param.equals(PARAMETER_AUDIO)
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

    public List<SortMethod> getSortMethods() {
        List<SortMethod> sortMethods = new ArrayList<>();
        for (SortMethodWrapper sortMethodWrapper : sortMethodWrappers) {
            SortMethod sortMethod;
            ParameterList params = sortMethodWrapper.parameters;
            final boolean addToArchive = params.contains(PARAMETER_ADD_TO_ARCHIVE);
            final boolean addToFilename = params.contains(PARAMETER_ADD_TO_FILENAME);
            switch (sortMethodWrapper.sortMethodType) {
                case SPLIT:
                    int filecount = params.getIntParamValue(PARAMETER_FILES_PER_DIR);
                    sortMethod = new SortMethodSplit(filecount, addToArchive, addToFilename);
                    break;
                case SIZE:
                    List<SizeRange> sizeRanges = new ArrayList<>();
                    for (int i=0; i<params.size(); i++) {
                        String paramName = getParamName(params.get(i));
                        switch (paramName) {
                            case PARAMETER_RANGE:
                                String rangeStr = params.getStringParamValueSafe(PARAMETER_RANGE, i);
                                if (rangeStr == null)
                                    throw new IllegalArgumentException("Error parsing " + PARAMETER_RANGE + " param value");
                                String[] ranges = rangeStr.split(PARAMETER_RANGE_SEPARATOR);
                                long min = Long.parseLong(ranges[0].trim());
                                long max = Long.parseLong(ranges[1].trim());
                                sizeRanges.add(new SizeRange(min, max));
                                break;
                            case PARAMETER_MIN:
                                min = params.getLongParamValueSafe(PARAMETER_MIN);
                                sizeRanges.add(new SizeRange(min, -1));
                                break;
                            case PARAMETER_MAX:
                                max = params.getLongParamValueSafe(PARAMETER_MAX);
                                sizeRanges.add(new SizeRange(-1, max));
                                break;
                        }
                    }
                    sortMethod = new SortMethodSize(sizeRanges, addToArchive, addToFilename);
                    break;
                case FILE_EXTENSION:
                    List<SortMethodExtension.ExtensionGroup> extensionGroups = new ArrayList<>();
                    for (int i=0; i<params.size(); i++) {
                        String paramName = getParamName(params.get(i));
                        SortMethodExtension.ExtensionGroup group = null;
                        switch (paramName) {
                            case PARAMETER_GROUP:
                                String groupStr = params.getStringParamValueSafe(PARAMETER_GROUP, i);
                                if (groupStr == null)
                                    throw new IllegalArgumentException("Error parsing " + PARAMETER_GROUP + " param value");
                                String[] strings = groupStr.split(PARAMETER_GROUP_SEPARATOR);
                                List<String> extensions = new ArrayList<>();
                                for (String s : strings) {
                                    extensions.add(s.trim());
                                }
                                group = new SortMethodExtension.ExtensionGroup(extensions);
                                break;
                            case PARAMETER_AUDIO:
                                group = new SortMethodExtension.ExtensionGroup(Arrays.asList(FileGroupAudio.AUDIO_EXTENSIONS));
                                break;
                            case PARAMETER_VIDEO:
                                group = new SortMethodExtension.ExtensionGroup(Arrays.asList(FileGroupVideo.VIDEO_EXTENSIONS));
                                break;
                            case PARAMETER_IMAGE:
                                group = new SortMethodExtension.ExtensionGroup(Arrays.asList(FileGroupImage.IMAGE_EXTENSIONS));
                                break;
                            case PARAMETER_DOCUMENT:
                                group = new SortMethodExtension.ExtensionGroup(Arrays.asList(FileGroupDocument.DOCUMENT_EXTENSIONS));
                                break;
                        }
                        if (group != null) extensionGroups.add(group);
                    }
                    sortMethod = new SortMethodExtension(extensionGroups, addToArchive, addToFilename);
                    break;
                case ALPHANUMERIC:
                    List<AlphanumRange> alphanumRanges = new ArrayList<>();
                    for (int i=0; i<params.size(); i++) {
                        String paramName = getParamName(params.get(i));
                        switch (paramName) {
                            case PARAMETER_RANGE:
                                String rangeStr = params.getStringParamValueSafe(PARAMETER_RANGE, i);
                                if (rangeStr == null)
                                    throw new IllegalArgumentException("Error parsing " + PARAMETER_RANGE + " param value");
                                String[] ranges = rangeStr.split(PARAMETER_RANGE_SEPARATOR);
                                String start = ranges[0].trim();
                                String end = ranges[1].trim();
                                alphanumRanges.add(new AlphanumRange(start, end));
                                break;
                            case PARAMETER_START:
                                start = params.getStringParamValueSafe(PARAMETER_START);
                                alphanumRanges.add(new AlphanumRange(start, null));
                                break;
                            case PARAMETER_END:
                                end = params.getStringParamValueSafe(PARAMETER_END);
                                alphanumRanges.add(new AlphanumRange(null, end));
                                break;
                        }
                    }
                    sortMethod = new SortMethodAlphanum(alphanumRanges, addToArchive, addToFilename);
                    break;
                case IMAGE_RESOLUTION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodImageRes(addToArchive, addToFilename);
                    break;
                case DATE:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodDate(addToArchive, addToFilename);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort method type");
            }
            sortMethods.add(sortMethod);
        }
        return sortMethods;
    }

}
