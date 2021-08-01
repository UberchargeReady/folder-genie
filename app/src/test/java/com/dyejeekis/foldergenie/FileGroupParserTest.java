package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.parser.FileGroupParser;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    FileGroupParser p1 = new FileGroupParser("alphanum");
    FileGroupParser p2 = new FileGroupParser("SIZE -param1");
    FileGroupParser p3 = new FileGroupParser("monthCreated -PARAM2  -param3");
    FileGroupParser p4 = new FileGroupParser("year modified - param4");
    FileGroupParser p5 = new FileGroupParser("invalidFileGroup");
    FileGroupParser p6 = new FileGroupParser("YearCreated - param5 -   param6 -param7");

    @Test
    public void testType() {
        assertSame(p1.getType(), FileGroupType.ALPHANUMERIC);
        assertSame(p2.getType(), FileGroupType.SIZE);
        assertSame(p3.getType(), FileGroupType.MONTH_CREATED);
        assertNull(p4.getType());
        assertNull(p5.getType());
        assertSame(p6.getType(), FileGroupType.YEAR_CREATED);
    }

    @Test
    public void testParameters() {
        assertTrue(p1.getParameters().isEmpty());
        assertEquals(p2.getParameters().get(0), "-param1");
        assertEquals(p3.getParameters().get(0), "-param2");
        assertEquals(p3.getParameters().get(1), "-param3");
        assertEquals(p6.getParameters().get(0), "-param7");
    }
}
