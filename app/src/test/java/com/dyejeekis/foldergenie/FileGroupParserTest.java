package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.OverlappingElementsException;
import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;
import com.dyejeekis.foldergenie.parser.FileGroupParser;
import com.dyejeekis.foldergenie.parser.InvalidParameterException;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    FileGroupParser parser;
    FileGroup fileGroup;

    @Test
    public void testFileGroupAll() {
        // valid 1
        parser = new FileGroupParser("all  ");
        assertEquals(parser.getType(), FileGroupType.ALL);
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.ALL);
        // valid 2
        parser = new FileGroupParser("ALL  -subDir");
        assertEquals(parser.getType(), FileGroupType.ALL);
        assertEquals(parser.getParameters().get(0), "-subDir");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.ALL);
        assertTrue(fileGroup.includeSubdirs());
        // invalid 1
        try {
            parser = new FileGroupParser("all-");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
        // invalid 2
        try {
            parser = new FileGroupParser("all --subdir");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testFileGroupSize() {
        // valid 1
        parser = new FileGroupParser("size -range 2000: 4000-range 6000 : 8000");
        assertEquals(parser.getType(), FileGroupType.SIZE);
        assertEquals(parser.getParameters().get(0), "-range 2000: 4000");
        assertEquals(parser.getParameters().get(1), "-range 6000 : 8000");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.SIZE);
        // valid 2
        parser = new FileGroupParser("size -max 4000-min 8000");
        assertEquals(parser.getType(), FileGroupType.SIZE);
        assertEquals(parser.getParameters().get(0), "-max 4000");
        assertEquals(parser.getParameters().get(1), "-min 8000");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.SIZE);
        // invalid 1
        parser = new FileGroupParser("size -range 6000:3000");
        assertEquals(parser.getType(), FileGroupType.SIZE);
        assertEquals(parser.getParameters().get(0), "-range 6000:3000");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        // invalid 2
        parser = new FileGroupParser("size -max 6000 -min 3000 ");
        assertEquals(parser.getType(), FileGroupType.SIZE);
        assertEquals(parser.getParameters().get(0), "-max 6000 ");
        assertEquals(parser.getParameters().get(1), "-min 3000 ");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
    }

    @Test
    public void testFileGroupName() {
        // valid 1
        parser = new FileGroupParser("name -range a : d -range e : h ");
        assertEquals(parser.getType(), FileGroupType.NAME);
        assertEquals(parser.getParameters().get(0), "-range a : d ");
        assertEquals(parser.getParameters().get(1), "-range e : h ");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.NAME);
        // valid 2
        parser = new FileGroupParser("name -to ep -range er:ev");
        assertEquals(parser.getType(), FileGroupType.NAME);
        assertEquals(parser.getParameters().get(0), "-to ep ");
        assertEquals(parser.getParameters().get(1), "-range er:ev");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.NAME);
        // invalid 1
        parser = new FileGroupParser("name -range a : c -from c");
        assertEquals(parser.getType(), FileGroupType.NAME);
        assertEquals(parser.getParameters().get(0), "-range a : c ");
        assertEquals(parser.getParameters().get(1), "-from c");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 2
        try {
            parser = new FileGroupParser("name -to cad -to dap");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testFileGroupDate() {
        // valid 1
        parser = new FileGroupParser("date -dateModified 3/10/1992");
        assertEquals(parser.getType(), FileGroupType.DATE);
        assertEquals(parser.getParameters().get(0), "-dateModified 3/10/1992");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.DATE);
        // valid 2
        parser = new FileGroupParser("date -month 2 -year 2018");
        assertEquals(parser.getType(), FileGroupType.DATE);
        assertEquals(parser.getParameters().get(0), "-month 2 ");
        assertEquals(parser.getParameters().get(1), "-year 2018");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.DATE);
        // invalid 1
        parser = new FileGroupParser("date -from 1/1/2000-to 1/1/2010");
        assertEquals(parser.getType(), FileGroupType.DATE);
        assertEquals(parser.getParameters().get(0), "-from 1/1/2000");
        assertEquals(parser.getParameters().get(1), "-to 1/1/2010");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 2
        parser = new FileGroupParser("date -range 1/1/1990:31/12/2000 -from 1/1/2000 ");
        assertEquals(parser.getType(), FileGroupType.DATE);
        assertEquals(parser.getParameters().get(0), "-range 1/1/1990:31/12/2000 ");
        assertEquals(parser.getParameters().get(1), "-from 1/1/2000 ");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 3
        parser = new FileGroupParser("date -dateModified 29/2/2021");
        assertEquals(parser.getType(), FileGroupType.DATE);
        assertEquals(parser.getParameters().get(0), "-dateModified 29/2/2021");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testFileGroupExtension() {
        // valid 1
        parser = new FileGroupParser("extension -select mp3, mp4, png, jpg");
        assertEquals(parser.getType(), FileGroupType.EXTENSION);
        assertEquals(parser.getParameters().get(0), "-select mp3, mp4, png, jpg");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.EXTENSION);
        // valid 2
        parser = new FileGroupParser("extension -group doc, xls -image ");
        assertEquals(parser.getType(), FileGroupType.EXTENSION);
        assertEquals(parser.getParameters().get(0), "-group doc, xls ");
        assertEquals(parser.getParameters().get(1), "-image ");
        fileGroup = parser.getFileGroup();
        assertEquals(fileGroup.getType(), FileGroupType.EXTENSION);
        // invalid 1
        try {
            parser = new FileGroupParser("extension -document -document");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
        // invalid 2
        parser = new FileGroupParser("extension -select xls, doc,  png-group avi,mp4,png,jpg");
        assertEquals(parser.getType(), FileGroupType.EXTENSION);
        assertEquals(parser.getParameters().get(0), "-select xls, doc,  png");
        assertEquals(parser.getParameters().get(1), "-group avi,mp4,png,jpg");
        try {
            fileGroup = parser.getFileGroup();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
    }
}
