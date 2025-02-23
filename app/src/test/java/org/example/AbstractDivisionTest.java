package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractDivisionTest {

    private final DivisionType m_divisionType;

    private static final String TEST_RESOURCES_PATH = "src/test/resources";

    public AbstractDivisionTest(DivisionType divisionType) {
        this.m_divisionType = divisionType;
    }

    @Test
    void addNewDivision() throws IOException {

        // read the master_file from test1
        // read new file new_file.txt
        // add the new division that's in the new file and create a new master file.

        var masterFileDivisions = DivisionDriver.getDivisions(m_divisionType,TEST_RESOURCES_PATH + "/test1/master_file.txt");
        var newFileDivisions = DivisionDriver.getDivisions(m_divisionType,TEST_RESOURCES_PATH + "/test1/new_file.txt");
        DivisionDriver.updateDivisions( m_divisionType, masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test1/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test1/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test1/new_master_file.txt")).stream().skip(1).toList());
    }

    @Test
    void updateDivisions() throws IOException {
        // read the master_file from test2
        // read new file updates_file.txt
        // update the division that's in the new file and create a new master file.

        var masterFileDivisions = DivisionDriver.getDivisions( m_divisionType,TEST_RESOURCES_PATH + "/test2/master_file.txt");
        var newFileDivisions = DivisionDriver.getDivisions(m_divisionType, TEST_RESOURCES_PATH + "/test2/updates_file.txt");
        DivisionDriver.updateDivisions(m_divisionType, masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test2/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test2/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test2/new_master_file.txt")).stream().skip(1).toList());
    }

    @Test
    void deleteDivisions() throws IOException {
        // read the master_file from test3
        // read new file updates_file.txt
        // update the division that's in the new file and create a new master file.
        // delete the division that's in the new file and create a new master file.

        var masterFileDivisions = DivisionDriver.getDivisions(m_divisionType,TEST_RESOURCES_PATH + "/test3/master_file.txt");
        var newFileDivisions = DivisionDriver.getDivisions(m_divisionType,TEST_RESOURCES_PATH + "/test3/updates_file.txt");
        DivisionDriver.updateDivisions(m_divisionType,masterFileDivisions, newFileDivisions, TEST_RESOURCES_PATH + "/test3/new_master_file.txt");

        assertEquals( Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test3/to_verify.txt")),
                Files.readAllLines(Paths.get(TEST_RESOURCES_PATH + "/test3/new_master_file.txt")).stream().skip(1).toList());
    }
}
