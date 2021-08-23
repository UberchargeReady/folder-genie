package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.parser.SortMethodParser;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class SortMethodParserTest {

    static final String INPUT_FOLDER_VALID_1 = "";
    static final String INPUT_FOLDER_VALID_2 = "";
    static final String INPUT_FOLDER_INVALID_1 = "";
    static final String INPUT_FOLDER_INVALID_2 = "";

    static final String INPUT_SPLIT_VALID_1 = "";
    static final String INPUT_SPLIT_VALID_2 = "";
    static final String INPUT_SPLIT_INVALID_1 = "";
    static final String INPUT_SPLIT_INVALID_2 = "";

    static final String INPUT_SIZE_VALID_1 = "";
    static final String INPUT_SIZE_VALID_2 = "";
    static final String INPUT_SIZE_INVALID_1 = "";
    static final String INPUT_SIZE_INVALID_2 = "";

    static final String INPUT_NAME_VALID_1 = "";
    static final String INPUT_NAME_VALID_2 = "";
    static final String INPUT_NAME_INVALID_1 = "";
    static final String INPUT_NAME_INVALID_2 = "";

    static final String INPUT_DATE_VALID_1 = "";
    static final String INPUT_DATE_VALID_2 = "";
    static final String INPUT_DATE_INVALID_1 = "";
    static final String INPUT_DATE_INVALID_2 = "";

    static final String INPUT_EXTENSION_VALID_1 = "";
    static final String INPUT_EXTENSION_VALID_2 = "";
    static final String INPUT_EXTENSION_INVALID_1 = "";
    static final String INPUT_EXTENSION_INVALID_2 = "";

    static final String INPUT_IMAGE_RES_VALID_1 = "";
    static final String INPUT_IMAGE_RES_VALID_2 = "";
    static final String INPUT_IMAGE_RES_INVALID_1 = "";
    static final String INPUT_IMAGE_RES_INVALID_2 = "";

    static SortMethodParser p1;
    static SortMethodParser p2;
    static SortMethodParser p3;
    static SortMethodParser p4;
    static SortMethodParser p5;
    static SortMethodParser p6;
    static SortMethodParser p7;

    @BeforeClass
    public static void init() {
        p1 = new SortMethodParser("fileextension");
        p2 = new SortMethodParser("YEARCREATED  -PARAM1, monthCreated");
        p3 = new SortMethodParser("split -param2 -param3 , size -param4");
        p4 = new SortMethodParser("file extension -param5,alphanum  -param6 -param7, imageRes");
        p5 = new SortMethodParser("invalidSortMethod");
        p6 = new SortMethodParser("split -param8, split -param9");
        try {
            p7 = new SortMethodParser("monthmodified - param10");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testType() {
        assertSame(p1.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.EXTENSION);
        assertSame(p2.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.YEAR_CREATED);
        assertSame(p2.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.MONTH_CREATED);
        assertSame(p3.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.SPLIT);
        assertSame(p3.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.SIZE);
        assertSame(p4.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.NAME);
        assertSame(p4.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.IMAGE_RESOLUTION);
        assertTrue(p5.getSortMethodWrappers().isEmpty());
        assertSame(p6.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.SPLIT);
        assertSame(p6.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.SPLIT);
        assertNull(p7);
    }

    @Test
    public void testParameters() {
        assertTrue(p1.getSortMethodWrappers().get(0).parameters.isEmpty());
        assertEquals(p2.getSortMethodWrappers().get(0).parameters.get(0), "-param1");
        assertTrue(p2.getSortMethodWrappers().get(1).parameters.isEmpty());
        assertEquals(p3.getSortMethodWrappers().get(0).parameters.get(0), "-param2");
        assertEquals(p3.getSortMethodWrappers().get(0).parameters.get(1), "-param3");
        assertEquals(p3.getSortMethodWrappers().get(1).parameters.get(0), "-param4");
        assertEquals(p4.getSortMethodWrappers().get(0).parameters.get(0), "-param6");
        assertEquals(p4.getSortMethodWrappers().get(0).parameters.get(1), "-param7");
        assertTrue(p4.getSortMethodWrappers().get(1).parameters.isEmpty());
        assertEquals(p6.getSortMethodWrappers().get(0).parameters.get(0), "-param8");
        assertEquals(p6.getSortMethodWrappers().get(1).parameters.get(0), "-param9");
    }
}
