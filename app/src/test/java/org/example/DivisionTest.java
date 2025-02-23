/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jagan
 */
public class DivisionTest {

    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    @Test
    void addNewDivision() throws IOException {

        // read the master_file from test1
        // read new file new_file.txt
        // add the new division that's in the new file and create a new master file.

        Map<String, Division> masterFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test1/master_file.txt");
        Map<String, Division> newFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test1/new_file.txt");
        DivisionDriver.updateDivisions(masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test1/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test1/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test1/new_master_file.txt")));
    }

    @Test
    void updateDivisions() throws IOException {
        // read the master_file from test2
        // read new file updates_file.txt
        // update the division that's in the new file and create a new master file.

        Map<String, Division> masterFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test2/master_file.txt");
        Map<String, Division> newFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test2/updates_file.txt");
        DivisionDriver.updateDivisions(masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test2/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test2/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test2/new_master_file.txt")));
    }

    @Test
    void deleteDivisions() throws IOException {
        // read the master_file from test3
        // read new file updates_file.txt
        // update the division that's in the new file and create a new master file.
        // delete the division that's in the new file and create a new master file.

        Map<String, Division> masterFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test3/master_file.txt");
        Map<String, Division> newFileDivisions = DivisionDriver.getDivisions(TEST_RESOURCES_PATH + "/test3/updates_file.txt");
        DivisionDriver.updateDivisions(masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test3/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test3/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test3/new_master_file.txt")));
    }
}