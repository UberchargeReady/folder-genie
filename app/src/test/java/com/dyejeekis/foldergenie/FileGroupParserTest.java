package com.dyejeekis.foldergenie;

import com.dyejeekis.foldergenie.model.filegroup.FileGroup;
import com.dyejeekis.foldergenie.parser.FileGroupParser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;

public class FileGroupParserTest {

    FileGroupParser parser;
    FileGroup fileGroup;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testFileGroupAll() {
        // TODO: 8/23/2021
        // valid 1
        parser = new FileGroupParser("all  ");
        // valid 2
        parser = new FileGroupParser("ALL  -subDir");
        // invalid 1
        exception.expect(IllegalArgumentException.class);
        parser = new FileGroupParser("all-");
        // invalid 2
        exception.expect(InvalidParameterException.class);
        parser = new FileGroupParser("all --subdir");
    }

    @Test
    public void testFileGroupSize() {
        // TODO: 8/23/2021
        // valid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // valid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
    }

    @Test
    public void testFileGroupName() {
        // TODO: 8/23/2021
        // valid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // valid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
    }

    @Test
    public void testFileGroupDate() {
        // TODO: 8/23/2021
        // valid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // valid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
    }

    @Test
    public void testFileGroupExtension() {
        // TODO: 8/23/2021
        // valid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // valid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 1
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
        // invalid 2
        parser = new FileGroupParser("");
        fileGroup = parser.getFileGroup();
    }
}
