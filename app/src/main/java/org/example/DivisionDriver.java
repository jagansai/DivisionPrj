package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DivisionDriver {

    private static final String COMMA = ",";
    private static final String PIPE_REGEX = "\\|";
    private static final String PIPE = "|";
    private static final String NEW_LINE = "\n";

    /**
     * Reads the divisions from a file and returns them as a map.
     * @param divisionType The type of division to create (Division or DivisionV2).
     * @param fileName The name of the file to read from.
     * @return A map of division names to IDivision objects.
     * @throws IOException If there is an error reading the file.
     */
    public static Map<String, IDivision> getDivisions(DivisionType divisionType, String fileName) throws IOException {
        try {
            return Files.readAllLines(Paths.get(fileName))
                    .stream()
                    .map(x -> x.split(COMMA))
                    .map(x -> createDivision(divisionType, x[0], Arrays.asList(x[1].split(PIPE_REGEX))))
                    .collect(Collectors.toMap(IDivision::getName, division -> division));
        } catch (IOException e) {
            throw new IOException("Failed to read divisions from file: " + fileName, e);
        }
    }

    /**
     * Creates a division based on the specified division type.
     * @param divisionType The type of division to create (Division or DivisionV2).
     * @param name The name of the division.
     * @param depts The list of departments in the division.
     * @return The created IDivision object.
     */
    private static IDivision createDivision(DivisionType divisionType, String name, List<String> depts) {
        return switch (divisionType) {
            case Division -> new Division(name, depts);
            case DivisionV2 -> new DivisionV2(name, depts);
        };
    }

    /**
     * Updates the master divisions with new divisions and writes them to a file.
     * @param divisionType The type of division to create (Division or DivisionV2).
     * @param masterDivision The map of master divisions.
     * @param newDivision The map of new divisions.
     * @param fileName The name of the file to write to.
     * @throws IOException If there is an error writing to the file.
     */
    public static void updateDivisions(DivisionType divisionType, Map<String, IDivision> masterDivision,
                                       Map<String, IDivision> newDivision, String fileName) throws IOException {

        StringBuilder sb = new StringBuilder();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(fileName),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bufferedWriter.write("# " + ZonedDateTime.now(ZoneId.of("Asia/Singapore")).toLocalTime() + NEW_LINE);
            for (var keyValue : masterDivision.entrySet()) {
                var division = newDivision.get(keyValue.getKey());
                if (division != null) {
                    newDivision.remove(keyValue.getKey());
                    writeToFile(keyValue, sb, division, bufferedWriter);
                }
            }
            for (var keyValue : newDivision.entrySet()) {
                writeToFile(keyValue, sb, keyValue.getValue(), bufferedWriter);
            }
        } catch (IOException e) {
            throw new IOException("Failed to update divisions in file: " + fileName, e);
        }
    }

    /**
     * Writes a division to a file.
     * @param keyValue The map entry containing the division name and object.
     * @param sb The StringBuilder for constructing the file line.
     * @param division The IDivision object to write.
     * @param bufferedWriter The BufferedWriter to write to the file.
     * @throws IOException If there is an error writing to the file.
     */
    private static void writeToFile(Map.Entry<String, IDivision> keyValue, StringBuilder sb,
                                    IDivision division, BufferedWriter bufferedWriter) throws IOException {
        sb.append(keyValue.getKey())
                .append(COMMA)
                .append(String.join(PIPE, division.getDepts()))
                .append(NEW_LINE);
        bufferedWriter.write(sb.toString());
        sb.setLength(0);
    }
}
