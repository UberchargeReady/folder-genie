package com.dyejeekis.foldergenie;

import org.junit.Test;

public class SortMethodParserTest {

    static final String INPUT_FOLDER_VALID_1 = "folder -name sample name";
    static final String INPUT_FOLDER_VALID_2 = "folder -name   test -archive";
    static final String INPUT_FOLDER_INVALID_1 = "folder";
    static final String INPUT_FOLDER_INVALID_2 = "folder - name test";

    static final String INPUT_SPLIT_VALID_1 = "split -filecount 20";
    static final String INPUT_SPLIT_VALID_2 = "split  -filecount   100";
    static final String INPUT_SPLIT_INVALID_1 = "split";
    static final String INPUT_SPLIT_INVALID_2 = "split -filecount5";

    static final String INPUT_SIZE_VALID_1 = "size -min 4343 -max 5344";
    static final String INPUT_SIZE_VALID_2 = "size -range 4344 to 43543";
    static final String INPUT_SIZE_INVALID_1 = "size -min 4344 -range 4345 to 434532";
    static final String INPUT_SIZE_INVALID_2 = "size -range 4000 to 5000 -range 4500 to 6000";

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
