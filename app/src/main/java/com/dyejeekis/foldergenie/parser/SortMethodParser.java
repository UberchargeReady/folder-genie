package com.dyejeekis.foldergenie.parser;

import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodMonth;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodName;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodDate;
import com.dyejeekis.foldergenie.model.sortmethod.extension.SortMethodExtension;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodFolder;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodImageRes;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodSize;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodSplit;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodYear;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
                String typeStr = s.split(PARAMETER_PREFIX)[0].trim().toLowerCase();
                if (typeStr.equals(type.name.toLowerCase())) {
                    wrappers.add(new SortMethodWrapper(type, parseParameters(s, type)));
                    break;
                }
            }
        }
        if (wrappers.isEmpty()) throw new IllegalArgumentException("Invalid sort method type");
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
        if (param.equals(PARAMETER_ADD_TO_ARCHIVE) || param.equals(PARAMETER_ADD_TO_FILENAME))
            return true;
        switch (type) {
            case SPLIT:
                return param.equals(PARAMETER_FILECOUNT);
            case FOLDER:
                return param.equals(PARAMETER_NAME);
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

    public List<SortMethod> getSortMethods() {
        List<SortMethod> sortMethods = new ArrayList<>();
        for (SortMethodWrapper sortMethodWrapper : sortMethodWrappers) {
            SortMethod sortMethod;
            ParameterList params = sortMethodWrapper.parameters;
            final boolean addToArchive = params.contains(PARAMETER_ADD_TO_ARCHIVE);
            final boolean addToFilename = params.contains(PARAMETER_ADD_TO_FILENAME);
            switch (sortMethodWrapper.sortMethodType) {
                case SPLIT:
                    if (params.size() == 0) throw new InvalidParameterException("Missing -"
                            + PARAMETER_FILECOUNT + " parameter");
                    int filecount = params.getIntParamValueSafe(PARAMETER_FILECOUNT);
                    if (filecount == -1) throw new IllegalArgumentException("Error parsing -"
                            + PARAMETER_FILECOUNT + " parameter value");
                    sortMethod = new SortMethodSplit(filecount, addToArchive, addToFilename);
                    break;
                case FOLDER:
                    if (params.size() == 0) throw new InvalidParameterException("Missing -"
                            + PARAMETER_NAME + " parameter");
                    String name = params.getStringParamValueSafe(PARAMETER_NAME);
                    if (name == null) throw new IllegalArgumentException("Error parsing -"
                            + PARAMETER_NAME + " parameter value");
                    sortMethod = new SortMethodFolder(name, addToArchive, addToFilename);
                    break;
                case SIZE:
                    sortMethod = new SortMethodSize(parseSizeRanges(params), addToArchive, addToFilename);
                    break;
                case EXTENSION:
                    sortMethod = new SortMethodExtension(parseExtensionGroups(params), addToArchive, addToFilename);
                    break;
                case NAME:
                    sortMethod = new SortMethodName(parseAlphanumRanges(params), addToArchive, addToFilename);
                    break;
                case IMAGE_RESOLUTION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodImageRes(addToArchive, addToFilename);
                    break;
                case DATE:
                    sortMethod = new SortMethodDate(parseDateRanges(params), addToArchive, addToFilename);
                    break;
                case YEAR:
                    sortMethod = new SortMethodYear(addToArchive, addToFilename);
                    break;
                case MONTH:
                    sortMethod = new SortMethodMonth(addToArchive, addToFilename);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort method type");
            }
            sortMethods.add(sortMethod);
        }
        return sortMethods;
    }

}
