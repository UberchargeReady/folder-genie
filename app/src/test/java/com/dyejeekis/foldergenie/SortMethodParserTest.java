package com.dyejeekis.foldergenie;

import org.junit.Test;

public class SortMethodParserTest {

    static final String INPUT_FOLDER_VALID_1 = "folder -name sample name";
    static final String INPUT_FOLDER_VALID_2 = "folder -name   test -archive";
    static final String INPUT_FOLDER_INVALID_1 = "folder";
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

    @Test
    public void testSortMethodFolder() {
        // TODO: 8/23/2021
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
