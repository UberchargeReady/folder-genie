package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.filegroup.FileGroupParser;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    FileGroupParser p1 = new FileGroupParser("alphanum");
    FileGroupParser p2 = new FileGroupParser("SIZE /includesubdirs");
    FileGroupParser p3 = new FileGroupParser("monthCreated/ includesubdirs");
    FileGroupParser p4 = new FileGroupParser("year MoDified");
    FileGroupParser p5 = new FileGroupParser("invalidFileGroup");

    @Test
    public void testType() {
        assertSame(p1.getType(), FileGroupType.ALPHANUMERIC);
        assertSame(p2.getType(), FileGroupType.SIZE);
        assertSame(p3.getType(), FileGroupType.MONTH_CREATED);
        assertSame(p4.getType(), FileGroupType.YEAR_MODIFIED);
        assertNull(p5.getType());
    }

    @Test
    public void testParameters() {
        assertTrue(p1.getParameters().isEmpty());
        assertEquals(p2.getParameters().get(0), FileGroupParser.PARAMETER_INCLUDE_SUBDIRS);
        assertEquals(p3.getParameters().get(0), FileGroupParser.PARAMETER_INCLUDE_SUBDIRS);
    }
}
