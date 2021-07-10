package com.dyejeekis.foldergenie.util;

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
        String s = get(indexOf(param));
        return Integer.parseInt(s.substring(param.length() + 1));
    }

    public long getLongParamValue(String param) {
        String s = get(indexOf(param));
        return Long.parseLong(s.substring(param.length() + 1));
    }

    public String getStringParamValue(String param) {
        String s = get(indexOf(param));
        return s.substring(param.length() + 1);
    }
}
