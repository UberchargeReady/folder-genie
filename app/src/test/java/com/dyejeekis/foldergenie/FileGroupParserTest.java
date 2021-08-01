package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.parser.FileGroupParser;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    static FileGroupParser p1;
    static FileGroupParser p2;
    static FileGroupParser p3;
    static FileGroupParser p4;
    static FileGroupParser p5;
    static FileGroupParser p6;

    @BeforeClass
    public static void init() {
        p1 = new FileGroupParser("alphanum");
        p2 = new FileGroupParser("SIZE -param1");
        p3 = new FileGroupParser("monthCreated -PARAM2  -param3");
        try {
            p4 = new FileGroupParser("year modified - param4");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }
        p5 = new FileGroupParser("invalidFileGroup");
        try {
            p6 = new FileGroupParser("YearCreated - param5 -   param6 -param7");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testType() {
        assertSame(p1.getType(), FileGroupType.ALPHANUMERIC);
        assertSame(p2.getType(), FileGroupType.SIZE);
        assertSame(p3.getType(), FileGroupType.MONTH_CREATED);
        assertNull(p4);
        assertNull(p5.getType());
        assertNull(p6);
    }

    @Test
    public void testParameters() {
        assertTrue(p1.getParameters().isEmpty());
        assertEquals(p2.getParameters().get(0), "-param1");
        assertEquals(p3.getParameters().get(0), "-param2");
        assertEquals(p3.getParameters().get(1), "-param3");
    }
}
