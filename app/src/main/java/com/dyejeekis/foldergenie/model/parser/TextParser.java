package com.dyejeekis.foldergenie.model.parser;

import com.dyejeekis.foldergenie.model.AlphanumRange;
import com.dyejeekis.foldergenie.model.DateFilter;
import com.dyejeekis.foldergenie.model.DateRange;
import com.dyejeekis.foldergenie.model.SizeRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TextParser {

    // text parser parameters, must be lower case strings

    public static final String COMMAND_SEPARATOR = ";";
    public static final String PARAMETER_PREFIX = "-";
    public static final String PARAMETER_INCLUDE_SUBDIRECTORIES = "subdir";
    public static final String PARAMETER_ADD_TO_ARCHIVE = "archive";
    public static final String PARAMETER_ADD_TO_FILENAME = "rename";
    public static final String PARAMETER_FILECOUNT = "filecount";
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
    public static final String PARAMETER_FROM = "from";
    public static final String PARAMETER_TO = "to";
    public static final String PARAMETER_AUDIO = "audio";
    public static final String PARAMETER_VIDEO = "videos";
    public static final String PARAMETER_IMAGE = "images";
    public static final String PARAMETER_DOCUMENT = "documents";
    public static final String PARAMETER_DATE = "date";
    public static final String PARAMETER_DATE_SEPARATOR = "/";
    public static final String PARAMETER_DATE_CREATED = "dateCreated";
    public static final String PARAMETER_DATE_MODIFIED = "dateModified";
    public static final String PARAMETER_YEAR = "year";
    public static final String PARAMETER_YEAR_CREATED = "yearCreated";
    public static final String PARAMETER_YEAR_MODIFIED = "yearModified";
    public static final String PARAMETER_MONTH = "month";
    public static final String PARAMETER_MONTH_CREATED = "monthCreated";
    public static final String PARAMETER_MONTH_MODIFIED = "monthModified";
    public static final String PARAMETER_RANGE_CREATED = "rangeCreated";
    public static final String PARAMETER_RANGE_MODIFIED = "rangeModified";
    public static final String PARAMETER_MIN_CREATED = "minCreated";
    public static final String PARAMETER_MIN_MODIFIED = "minModified";
    public static final String PARAMETER_MAX_CREATED = "maxCreated";
    public static final String PARAMETER_MAX_MODIFIED = "maxModified";
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_CASE_SENSITIVE = ""; // TODO: 8/23/2021

    public static final String[] UNIQUE_PARAMETERS = {PARAMETER_MIN, PARAMETER_MAX, PARAMETER_START,
            PARAMETER_END, PARAMETER_FROM, PARAMETER_TO, PARAMETER_AUDIO, PARAMETER_VIDEO, PARAMETER_IMAGE,
            PARAMETER_DOCUMENT, PARAMETER_MIN_CREATED, PARAMETER_MIN_MODIFIED, PARAMETER_MAX_CREATED,
            PARAMETER_MAX_MODIFIED, PARAMETER_NAME, PARAMETER_CASE_SENSITIVE};

    protected String input;

    public TextParser(String input) {
        this.input = sanitizeInput(input);
    }

    protected String sanitizeInput(String input) {
        // TODO: 8/23/2021
        return input;
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
                return s.substring(1, end).toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected List<SizeRange> parseSizeRanges(ParameterList params) {
        List<SizeRange> sizeRanges = new ArrayList<>();
        for (int i=0; i<params.size(); i++) {
            String paramName = getParamName(params.get(i));
            switch (paramName) {
                case PARAMETER_RANGE:
                    String rangeStr = params.getStringParamValueSafe(paramName, i);
                    if (rangeStr == null) throw new IllegalArgumentException("Error parsing -"
                            + paramName + " parameter value");
                    String[] ranges = rangeStr.split(PARAMETER_RANGE_SEPARATOR);
                    long min = Long.parseLong(ranges[0].trim());
                    long max = Long.parseLong(ranges[1].trim());
                    sizeRanges.add(new SizeRange(min, max));
                    break;
                case PARAMETER_MIN:
                    min = params.getLongParamValueSafe(paramName);
                    sizeRanges.add(new SizeRange(min, -1));
                    break;
                case PARAMETER_MAX:
                    max = params.getLongParamValueSafe(paramName);
                    sizeRanges.add(new SizeRange(-1, max));
                    break;
            }
        }
        return sizeRanges;
    }

    protected List<AlphanumRange> parseAlphanumRanges(ParameterList params) {
        List<AlphanumRange> alphanumRanges = new ArrayList<>();
        for (int i=0; i<params.size(); i++) {
            String paramName = getParamName(params.get(i));
            switch (paramName) {
                case PARAMETER_RANGE:
                    String rangeStr = params.getStringParamValueSafe(paramName, i);
                    if (rangeStr == null) throw new IllegalArgumentException("Error parsing -"
                            + paramName + " parameter value");
                    String[] ranges = rangeStr.split(PARAMETER_RANGE_SEPARATOR);
                    String start = ranges[0].trim();
                    String end = ranges[1].trim();
                    alphanumRanges.add(new AlphanumRange(start, end));
                    break;
                case PARAMETER_FROM:
                    start = params.getStringParamValueSafe(paramName);
                    alphanumRanges.add(new AlphanumRange(start, null));
                    break;
                case PARAMETER_TO:
                    end = params.getStringParamValueSafe(paramName);
                    alphanumRanges.add(new AlphanumRange(null, end));
                    break;
                case PARAMETER_CASE_SENSITIVE:
                    // TODO: 8/23/2021
                    break;
            }
        }
        return alphanumRanges;
    }

    protected List<DateRange> parseDateRanges(ParameterList params) {
        List<DateRange> dateRanges = new ArrayList<>();
        for (int i=0; i<params.size(); i++) {
            String paramName = getParamName(params.get(i));
            switch (paramName) {
                case PARAMETER_FROM:
                case PARAMETER_MIN_MODIFIED:
                    String fromStr = params.getStringParamValueSafe(paramName);
                    dateRanges.add(new DateRange(parseDateFilter(fromStr), null));
                    break;
                case PARAMETER_TO:
                case PARAMETER_MAX_MODIFIED:
                    String toStr = params.getStringParamValueSafe(paramName);
                    dateRanges.add(new DateRange(null, parseDateFilter(toStr)));
                    break;
                case PARAMETER_RANGE:
                case PARAMETER_RANGE_MODIFIED:
                    String rangeStr = params.getStringParamValueSafe(paramName, i);
                    if (rangeStr == null) throw new IllegalArgumentException("Error parsing -"
                            + paramName + " parameter value");
                    String[] ranges = rangeStr.split(PARAMETER_RANGE_SEPARATOR);
                    fromStr = ranges[0].trim();
                    toStr = ranges[1].trim();
                    dateRanges.add(new DateRange(parseDateFilter(fromStr), parseDateFilter(toStr)));
                    break;
                case PARAMETER_DATE:
                case PARAMETER_DATE_MODIFIED:
                    String dateStr = params.getStringParamValueSafe(paramName, i);
                    DateFilter dateFilter = parseDateFilter(dateStr);
                    dateRanges.add(new DateRange(dateFilter, dateFilter));
                    break;
                case PARAMETER_MONTH:
                case PARAMETER_MONTH_MODIFIED:
                    int month = params.getIntParamValueSafe(paramName, i);
                    dateFilter = new DateFilter.Builder().month(month).build();
                    dateRanges.add(new DateRange(dateFilter, dateFilter));
                    break;
                case PARAMETER_YEAR:
                case PARAMETER_YEAR_MODIFIED:
                    int year = params.getIntParamValueSafe(paramName, i);
                    dateFilter = new DateFilter.Builder().year(year).build();
                    dateRanges.add(new DateRange(dateFilter, dateFilter));
                    break;
            }
        }
        return dateRanges;
    }

    private DateFilter parseDateFilter(String dateStr) {
        String[] strings = dateStr.split(PARAMETER_DATE_SEPARATOR);
        int day = Integer.parseInt(strings[0]);
        int month = Integer.parseInt(strings[1]);
        int year = Integer.parseInt(strings[2]);
        return new DateFilter.Builder().day(day).month(month).year(year).build();
    }
}
