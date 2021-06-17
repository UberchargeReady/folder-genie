package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.filegroup.FileGroupParser;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    FileGroupParser p1 = new FileGroupParser("alphanum");
    FileGroupParser p2 = new FileGroupParser("SIZE -param1");
    FileGroupParser p3 = new FileGroupParser("monthCreated -PARAM2  -param3");
    FileGroupParser p4 = new FileGroupParser("year modified - param4");
    FileGroupParser p5 = new FileGroupParser("invalidFileGroup");

    @Test
    public void testType() {
        assertSame(p1.getType(), FileGroupType.ALPHANUMERIC);
        assertSame(p2.getType(), FileGroupType.SIZE);
        assertSame(p3.getType(), FileGroupType.MONTH_CREATED);
        assertNull(p4.getType());
        assertNull(p5.getType());
    }

    @Test
    public void testParameters() {
        assertTrue(p1.getParameters().isEmpty());
        assertEquals(p2.getParameters().get(0), "-param1");
        assertEquals(p3.getParameters().get(0), "-param2");
        assertEquals(p3.getParameters().get(1), "-param3");
    }
}
