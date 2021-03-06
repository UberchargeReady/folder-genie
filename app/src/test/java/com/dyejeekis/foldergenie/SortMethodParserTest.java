package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.OverlappingElementsException;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.parser.InvalidParameterException;
import com.dyejeekis.foldergenie.parser.SortMethodParser;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

public class SortMethodParserTest {

    SortMethodParser parser;
    List<SortMethodParser.SortMethodWrapper> wrappers;
    List<SortMethod> sortMethods;

    @Test
    public void testSortMethodFolder() {
        // valid 1
        parser = new SortMethodParser("folder -name sample name");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.size(), 1);
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-name sample name");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.FOLDER);
        assertEquals(sortMethods.get(0).getDirName(new File("test.jpg")), "sample name");
        // valid 2
        parser = new SortMethodParser("folder -name   test -archive");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.size(), 1);
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-name   test ");
        assertEquals(wrappers.get(0).parameters.get(1), "-archive");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.FOLDER);
        assertEquals(sortMethods.get(0).getDirName(new File("test.jpg")), "test");
        assertTrue(sortMethods.get(0).addToArchive());
        // invalid 1
        parser = new SortMethodParser("folder -archive");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-archive");
        try {
            parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            // TODO: 9/5/2021 should be missing parameter exception
            assertTrue(e instanceof IllegalArgumentException);
        }
        // invalid 2
        try {
            parser = new SortMethodParser("folder - name test");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testSortMethodSplit() {
        // valid 1
        parser = new SortMethodParser("split -filecount 20");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.SPLIT);
        assertEquals(wrappers.get(0).parameters.get(0), "-filecount 20");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.SPLIT);
        // valid 2
        parser = new SortMethodParser("folder  -name split files ; split  -filecount   100");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-name split files ");
        assertEquals(wrappers.get(1).sortMethodType, SortMethodType.SPLIT);
        assertEquals(wrappers.get(1).parameters.get(0), "-filecount   100");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.FOLDER);
        assertEquals(sortMethods.get(0).getDirName(new File("test.jpg")), "split files");
        assertEquals(sortMethods.get(1).getType(), SortMethodType.SPLIT);
        // invalid 1
        try {
            parser = new SortMethodParser("split -filecount 10 -filecount 20");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
        // invalid 2
        try {
            parser = new SortMethodParser("split -filecount5");
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidParameterException);
        }
    }

    @Test
    public void testSortMethodSize() {
        // valid 1
        parser = new SortMethodParser("size -range 6000:8000 -max 5000");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.SIZE);
        assertEquals(wrappers.get(0).parameters.get(0), "-range 6000:8000 ");
        assertEquals(wrappers.get(0).parameters.get(1), "-max 5000");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.SIZE);
        // valid 2
        parser = new SortMethodParser("size -range 2000 : 4000-min 5000 ");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.SIZE);
        assertEquals(wrappers.get(0).parameters.get(0), "-range 2000 : 4000");
        assertEquals(wrappers.get(0).parameters.get(1), "-min 5000 ");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.SIZE);
        // invalid 1
        parser = new SortMethodParser("size -min 4000 -range 4500 : 9000");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.SIZE);
        assertEquals(wrappers.get(0).parameters.get(0), "-min 4000 ");
        assertEquals(wrappers.get(0).parameters.get(1), "-range 4500 : 9000");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 2
        parser = new SortMethodParser("size -range 4000:5000 -range 4500 : 6000");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.SIZE);
        assertEquals(wrappers.get(0).parameters.get(0), "-range 4000:5000 ");
        assertEquals(wrappers.get(0).parameters.get(1), "-range 4500 : 6000");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
    }

    @Test
    public void testSortMethodName() {
        // valid 1
        parser = new SortMethodParser("name -from get -range aba : cad");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.NAME);
        assertEquals(wrappers.get(0).parameters.get(0), "-from get ");
        assertEquals(wrappers.get(0).parameters.get(1), "-range aba : cad");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.NAME);
        // valid 2
        parser = new SortMethodParser("name -from j -to i");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.NAME);
        assertEquals(wrappers.get(0).parameters.get(0), "-from j ");
        assertEquals(wrappers.get(0).parameters.get(1), "-to i");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.NAME);
        // invalid 1
        parser = new SortMethodParser("name -range b : f -range c : d");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.NAME);
        assertEquals(wrappers.get(0).parameters.get(0), "-range b : f ");
        assertEquals(wrappers.get(0).parameters.get(1), "-range c : d");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 2
        parser = new SortMethodParser("name -from p -to y");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.NAME);
        assertEquals(wrappers.get(0).parameters.get(0), "-from p ");
        assertEquals(wrappers.get(0).parameters.get(1), "-to y");
        try {
            sortMethods = parser.getSortMethods();
            // TODO: 9/5/2021
            //Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
    }

    @Test
    public void testSortMethodDate() {
        // valid 1
        parser = new SortMethodParser("date -year 1990");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.DATE);
        assertEquals(wrappers.get(0).parameters.get(0), "-year 1990");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.DATE);
        // valid 2
        parser = new SortMethodParser("date  -range 1/1/2000 : 1/1/2004 -from 1/1/2006");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.DATE);
        assertEquals(wrappers.get(0).parameters.get(0), "-range 1/1/2000 : 1/1/2004 ");
        assertEquals(wrappers.get(0).parameters.get(1), "-from 1/1/2006");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getType(), SortMethodType.DATE);
        // invalid 1
        parser = new SortMethodParser("date -month 25");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.DATE);
        assertEquals(wrappers.get(0).parameters.get(0), "-month 25");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
        // invalid 2
        parser = new SortMethodParser("date -to 1/1/1990  -range  1/1/1988 :1/1/1992");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.DATE);
        assertEquals(wrappers.get(0).parameters.get(0), "-to 1/1/1990  ");
        assertEquals(wrappers.get(0).parameters.get(1), "-range  1/1/1988 :1/1/1992");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
    }

    @Test
    public void testSortMethodExtension() {
        // valid 1
        parser = new SortMethodParser("extension -audio  -video  ");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.EXTENSION);
        assertEquals(wrappers.get(0).parameters.get(0), "-audio  ");
        assertEquals(wrappers.get(0).parameters.get(1), "-video  ");
        sortMethods = parser.getSortMethods();
        // valid 2
        parser = new SortMethodParser("extension -image -group doc, docx, xls -select mp4,avi");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.EXTENSION);
        assertEquals(wrappers.get(0).parameters.get(0), "-image ");
        assertEquals(wrappers.get(0).parameters.get(1), "-group doc, docx, xls ");
        assertEquals(wrappers.get(0).parameters.get(2), "-select mp4,avi");
        sortMethods = parser.getSortMethods();
        // invalid 1
        parser = new SortMethodParser("extension -document -select doc, jpg, png");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.EXTENSION);
        assertEquals(wrappers.get(0).parameters.get(0), "-document ");
        assertEquals(wrappers.get(0).parameters.get(1), "-select doc, jpg, png");
        try {
            sortMethods = parser.getSortMethods();
            Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof OverlappingElementsException);
        }
        // invalid 2
        parser = new SortMethodParser("extension -group .jpg,/png");
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.EXTENSION);
        try {
            sortMethods = parser.getSortMethods();
            // TODO: 9/3/2021
            //Assert.fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
}
