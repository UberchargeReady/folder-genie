package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.sortmethod.SortMethod;
import com.dyejeekis.foldergenie.model.sortmethod.SortMethodType;
import com.dyejeekis.foldergenie.parser.SortMethodParser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

public class SortMethodParserTest {

    static final String INPUT_FOLDER_VALID_1 = "folder -name sample name";
    static final String INPUT_FOLDER_VALID_2 = "folder -name   test -archive";
    static final String INPUT_FOLDER_INVALID_1 = "folder -archive";
    static final String INPUT_FOLDER_INVALID_2 = "folder - name test";

    static final String INPUT_SPLIT_VALID_1 = "split -filecount 20";
    static final String INPUT_SPLIT_VALID_2 = "split  -filecount   100";
    static final String INPUT_SPLIT_INVALID_1 = "split -filecount 10 -filecount 20";
    static final String INPUT_SPLIT_INVALID_2 = "split -filecount5";

    static final String INPUT_SIZE_VALID_1 = "size -range 6000:8000 -max 5000";
    static final String INPUT_SIZE_VALID_2 = "size -range 2000 : 4000 -min 5000";
    static final String INPUT_SIZE_INVALID_1 = "size -min 4000 -range 4500 : 9000";
    static final String INPUT_SIZE_INVALID_2 = "size -range 4000 to 5000 -range 4500 : 6000";

    static final String INPUT_NAME_VALID_1 = "name -from get -range aba : cad";
    static final String INPUT_NAME_VALID_2 = "name -min j -max i";
    static final String INPUT_NAME_INVALID_1 = "name -range b : f -range c : d";
    static final String INPUT_NAME_INVALID_2 = "name -min p -max y";

    static final String INPUT_DATE_VALID_1 = "date -year 1990";
    static final String INPUT_DATE_VALID_2 = "date  -range 1/1/2000 : 1/1/2004 -from 1/1/2006";
    static final String INPUT_DATE_INVALID_1 = "date -month 25";
    static final String INPUT_DATE_INVALID_2 = "date -to 1/1/1990  -range  1/1/1988 :1/1/1992";

    static final String INPUT_EXTENSION_VALID_1 = "extension -audio  -videos";
    static final String INPUT_EXTENSION_VALID_2 = "extension -images -group doc, docx, xls";
    static final String INPUT_EXTENSION_INVALID_1 = "extension -documents -documents";
    static final String INPUT_EXTENSION_INVALID_2 = "extension -group .jpg,/png";

    static final String INPUT_IMAGE_RES_VALID_1 = "";
    static final String INPUT_IMAGE_RES_VALID_2 = "";
    static final String INPUT_IMAGE_RES_INVALID_1 = "";
    static final String INPUT_IMAGE_RES_INVALID_2 = "";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testSortMethodFolder() {
        List<SortMethodParser.SortMethodWrapper> wrappers;
        List<SortMethod> sortMethods;
        SortMethodParser parser = new SortMethodParser(INPUT_FOLDER_VALID_1);
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.size(), 1);
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-name sample name");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getDirName(new File("test.jpg")), "sample name");

        parser = new SortMethodParser(INPUT_FOLDER_VALID_2);
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.size(), 1);
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-name   test ");
        assertEquals(wrappers.get(0).parameters.get(1), "-archive");
        sortMethods = parser.getSortMethods();
        assertEquals(sortMethods.get(0).getDirName(new File("test.jpg")), "test");
        assertTrue(sortMethods.get(0).addToArchive());

        parser = new SortMethodParser(INPUT_FOLDER_INVALID_1);
        wrappers = parser.getSortMethodWrappers();
        assertEquals(wrappers.get(0).sortMethodType, SortMethodType.FOLDER);
        assertEquals(wrappers.get(0).parameters.get(0), "-archive");
        exception.expect(IllegalArgumentException.class);
        parser.getSortMethods();

        exception.expect(InvalidParameterException.class);
        new SortMethodParser(INPUT_FOLDER_INVALID_2);
    }

    @Test
    public void testSortMethodSplit() {
        // TODO: 8/23/2021
    }

    @Test
    public void testSortMethodSize() {
        // TODO: 8/23/2021
    }

    @Test
    public void testSortMethodName() {
        // TODO: 8/23/2021
    }

    @Test
    public void testSortMethodDate() {
        // TODO: 8/23/2021
    }

    @Test
    public void testSortMethodExtension() {
        // TODO: 8/23/2021
    }

    @Test
    public void testSortMethodImageRes() {
        // TODO: 8/23/2021
    }
}
