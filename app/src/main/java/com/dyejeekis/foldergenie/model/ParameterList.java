package com.dyejeekis.foldergenie.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ParameterList extends ArrayList<String> {

    @Override
    public int indexOf(@Nullable Object o) {
        if (o instanceof String) {
            String s = (String) o;
            for (int i=0; i < size(); i++) {
                if (get(i).contains(s))
                    return i;
            }
        }
        return -1;
    }

    public int getIntParamValue(String param) {
        return getIntParamValue(param, indexOf(param));
    }

    public int getIntParamValue(String param, int index) {
        String s = get(index);
        return Integer.parseInt(s.substring(param.length() + 1));
    }

    public long getLongParamValue(String param) {
        return getLongParamValue(param, indexOf(param));
    }

    public long getLongParamValue(String param, int index) {
        String s = get(index);
        return Long.parseLong(s.substring(param.length() + 1));
    }

    public String getStringParamValue(String param) {
        return getStringParamValue(param, indexOf(param));
    }

    public String getStringParamValue(String param, int index) {
        String s = get(index);
        return s.substring(param.length() + 1);
    }
}
