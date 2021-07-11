package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.util.ParameterList;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ParameterListTest {

    ParameterList p1 = new ParameterList();

    @Before
    public void init() {
        p1.add("-param1423424");
        p1.add("-param2543525325442");
        p1.add("-param3fajsdf");
    }

    @Test
    public void testContains() {
        assertTrue(p1.contains("param1"));
        assertTrue(p1.contains("param2"));
        assertTrue(p1.contains("param3"));
    }

    @Test
    public void testValues() {
        assertEquals(p1.getIntParamValue("param1"), 423424);
        assertEquals(p1.getLongParamValue("param2"), 543525325442L);
        assertEquals(p1.getStringParamValue("param3"), "fajsdf");
    }
}
