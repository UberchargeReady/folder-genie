package com.dyejeekis.foldergenie.model.parser;

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
import java.util.List;

public class SortMethodParser extends TextParser {

    public static final String TAG = SortMethodParser.class.getSimpleName();

    public static final String SORT_METHOD_SEPARATOR = ",";

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
        int start = s.indexOf(PARAMETER_PREFIX);
        while (start != -1) {
            int end = s.indexOf(PARAMETER_PREFIX, start + PARAMETER_PREFIX.length());
            if (end == -1) end = s.length();

            String paramString = s.substring(start, end);
            if (isValidParam(getParamName(paramString), type)) {
                paramString = sanitizeParam(paramString);
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
                    sortMethod = new SortMethodSize(addToArchive, addToFilename);
                    for (int i=0; i<params.size(); i++) {
                        if (params.get(i).contains(PARAMETER_RANGE)) {
                            String range = params.getStringParamValue(PARAMETER_RANGE, i);
                            long min = Long.parseLong(range.split(PARAMETER_RANGE_SEPARATOR)[0]);
                            long max = Long.parseLong(range.split(PARAMETER_RANGE_SEPARATOR)[1]);
                            ((SortMethodSize) sortMethod).addSizeRange(new SizeRange(min, max));
                        }
                    }
                    if (((SortMethodSize) sortMethod).getSizeRanges().size() == 0)
                        throw new IllegalArgumentException("Sort method must have at least one size range to be valid");
                    break;
                case FILE_EXTENSION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodExtension(addToArchive, addToFilename);
                    break;
                case ALPHANUMERIC:
                    sortMethod = new SortMethodAlphanum(addToArchive, addToFilename);
                    for (int i=0; i<params.size(); i++) {
                        String range = params.getStringParamValue(PARAMETER_RANGE, i);
                        String start = range.split(PARAMETER_RANGE_SEPARATOR)[0].trim();
                        String end = range.split(PARAMETER_RANGE_SEPARATOR)[1].trim();
                        ((SortMethodAlphanum) sortMethod).addAlphanumRange(new AlphanumRange(start, end));
                    }
                    if (((SortMethodAlphanum) sortMethod).getAlphanumRanges().size() == 0)
                        throw new IllegalArgumentException("Sort method must have at least one alphanum range to be valid");
                    break;
                case IMAGE_RESOLUTION:
                    // TODO: 7/10/2021
                    sortMethod = new SortMethodImageRes(addToArchive, addToFilename);
                    break;
                case DATE_CREATED:
                case YEAR_CREATED:
                case DATE_MODIFIED:
                case MONTH_CREATED:
                case YEAR_MODIFIED:
                case MONTH_MODIFIED:
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
