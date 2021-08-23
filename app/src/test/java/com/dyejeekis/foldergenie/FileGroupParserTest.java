package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.parser.FileGroupParser;
import com.dyejeekis.foldergenie.model.filegroup.FileGroupType;

import org.junit.BeforeClass;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class FileGroupParserTest {

    static final String INPUT_ALL_VALID_1 = "all  ";
    static final String INPUT_ALL_VALID_2 = "ALL  -subDir";
    static final String INPUT_ALL_INVALID_1 = "all-";
    static final String INPUT_ALL_INVALID_2 = "all --subdir";

    static final String INPUT_SIZE_VALID_1 = "";
    static final String INPUT_SIZE_VALID_2 = "";
    static final String INPUT_SIZE_INVALID_1 = "";
    static final String INPUT_SIZE_INVALID_2 = "";

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

    @Test
    public void testFileGroupAll() {
        // TODO: 8/23/2021
    }

    @Test
    public void testFileGroupSize() {
        // TODO: 8/23/2021
    }

    @Test
    public void testFileGroupName() {
        // TODO: 8/23/2021
    }

    @Test
    public void testFileGroupDate() {
        // TODO: 8/23/2021
    }

    @Test
    public void testFileGroupExtension() {
        // TODO: 8/23/2021
    }
}
