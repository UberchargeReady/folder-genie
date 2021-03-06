package com.dyejeekis.foldergenie.parser;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParameterList extends ArrayList<String> {

    private List<String> uniqueParams = new ArrayList<>();

    @Override
    public int indexOf(@Nullable Object o) {
        if (o instanceof String) {
            String s = (String) o;
            for (int i=0; i < size(); i++) {
                if (get(i).toLowerCase().contains(s))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public boolean add(String s) {
        uniqueParamCheck(s);
        return super.add(s);
    }

    @Override
    public void add(int index, String element) {
        uniqueParamCheck(element);
        super.add(index, element);
    }

    private void uniqueParamCheck(String paramString) {
        String paramName = TextParser.getParamName(paramString);
        if (this.contains(TextParser.PARAMETER_PREFIX + paramName) && uniqueParams.contains(paramName))
            throw new InvalidParameterException("Illegal attempt to add a unique parameter twice: " + paramString);
    }

    private void valueCheck(String paramString) {
        if (!paramString.contains(" "))
            throw new IllegalArgumentException("Parameter doesn't have a value");
    }

    public List<String> getUniqueParams() {
        return uniqueParams;
    }

    public void setUniqueParams(List<String> uniqueParams) {
        this.uniqueParams = uniqueParams;
    }

    public void setUniqueParams(String[] uniqueParams) {
        setUniqueParams(Arrays.asList(uniqueParams));
    }

    public void addUniqueParam(String param) {
        this.uniqueParams.add(param);
    }

    public int getIntParamValue(String param) {
        return getIntParamValue(param, indexOf(TextParser.PARAMETER_PREFIX + param));
    }

    public int getIntParamValueSafe(String param) {
        try {
            return getIntParamValue(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getIntParamValue(String param, int index) {
        String s = get(index);
        valueCheck(s);
        return Integer.parseInt(s.substring(param.length() + 1).trim());
    }

    public int getIntParamValueSafe(String param, int index) {
        try {
            return getIntParamValue(param, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long getLongParamValue(String param) {
        return getLongParamValue(param, indexOf(TextParser.PARAMETER_PREFIX + param));
    }

    public long getLongParamValueSafe(String param) {
        try {
            return getLongParamValue(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public long getLongParamValue(String param, int index) {
        String s = get(index);
        valueCheck(s);
        return Long.parseLong(s.substring(param.length() + 1).trim());
    }

    public long getLongParamValueSafe(String param, int index) {
        try {
            return getLongParamValue(param, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getStringParamValue(String param) {
        return getStringParamValue(param, indexOf(TextParser.PARAMETER_PREFIX + param));
    }

    public String getStringParamValueSafe(String param) {
        try {
            return getStringParamValue(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringParamValue(String param, int index) {
        String s = get(index);
        valueCheck(s);
        return s.substring(param.length() + 1).trim();
    }

    public String getStringParamValueSafe(String param, int index) {
        try {
            return getStringParamValue(param, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
