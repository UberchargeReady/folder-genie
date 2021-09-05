package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.parser.InvalidParameterException;
import com.dyejeekis.foldergenie.parser.ParameterList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterListTest {

    ParameterList paramList;

    @Before
    public void init() {
        paramList = new ParameterList();
        // valid
        paramList.add("-param1 43434344342553");
        paramList.add("-param2     34");
        paramList.add("-param3   testValue");
        paramList.add("-param2 -54");
        paramList.add("-param3 string value");
        // invalid
        paramList.add("param4 34389743497343");
        paramList.add("-param5 4 56");
        paramList.add("-param6testValue");
    }

    @Test
    public void testValues() {
        assertEquals(paramList.getLongParamValueSafe("param1"), 43434344342553L);
        assertEquals(paramList.getIntParamValueSafe("param2"), 34);
        assertEquals(paramList.getStringParamValueSafe("param3"), "testValue");
        assertEquals(paramList.getIntParamValueSafe("param2", 3), -54);
        assertEquals(paramList.getStringParamValueSafe("param3", 4), "string value");

        assertEquals(paramList.getLongParamValueSafe("param4"), -1);
        assertEquals(paramList.getIntParamValueSafe("param5"), -1);
        assertNull(paramList.getStringParamValueSafe("param6"));
    }

    @Test
    public void testUnique() {
        paramList.addUniqueParam("param1");
        try {
            paramList.add("-param1 434793444739");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }
}
