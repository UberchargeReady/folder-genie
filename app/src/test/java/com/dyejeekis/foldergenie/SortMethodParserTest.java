package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.sortmethod.SortMethodParser;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortMethodParserTest {

    SortMethodParser p1 = new SortMethodParser("fileextension");
    SortMethodParser p2 = new SortMethodParser("YEARCREATED  -PARAM1, monthCreated");
    SortMethodParser p3 = new SortMethodParser("split -param2 -param3 , size -param4");
    SortMethodParser p4 = new SortMethodParser("file extension -param5,alphanum  -param6 -param7, imageRes");
    SortMethodParser p5 = new SortMethodParser("invalidSortMethod");

    @Test
    public void testType() {
        assertSame(p1.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.FILE_EXTENSION);
        assertSame(p2.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.YEAR_CREATED);
        assertSame(p2.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.MONTH_CREATED);
        assertSame(p3.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.SPLIT);
        assertSame(p3.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.SIZE);
        assertSame(p4.getSortMethodWrappers().get(0).sortMethodType, SortMethodType.ALPHANUMERIC);
        assertSame(p4.getSortMethodWrappers().get(1).sortMethodType, SortMethodType.IMAGE_RESOLUTION);
        assertTrue(p5.getSortMethodWrappers().isEmpty());
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
    }
}
