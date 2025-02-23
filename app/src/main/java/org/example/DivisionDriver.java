package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DivisionDriver {

    private static final String COMMA = ",";
    private static final String PIPE_REGEX = "\\|";
    private static final String PIPE = "|";
    private static final String NEW_LINE = "\n";


    public static Map<String, Division> getDivisions(String fileName) throws IOException {

        return Files.readAllLines(Paths.get(fileName))
                .stream()
                .map(x -> x.split(COMMA))
                .map(x -> new Division(x[0], Arrays.asList(x[1].split(PIPE_REGEX))))
                .collect(Collectors.toMap(Division::getName, division -> division));
    }

    public static void updateDivisions(Map<String, Division> masterDivision,
                                       Map<String, Division> newDivision,
                                       String fileName) throws IOException {

        Map<String, Division> unionOfDivisions = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(fileName),StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            bufferedWriter.write("# " + String.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Singapore")).toLocalTime()) + NEW_LINE);
            for (Map.Entry<String, Division> keyValue : masterDivision.entrySet()) {
                Division division = newDivision.get(keyValue.getKey()); // if we have the division, then update the master.
                if (division != null) { // new file has the division ( maybe with updated depts )
                    unionOfDivisions.put(division.getName(), new Division(division.getName(), division.getDepts()));
                    newDivision.remove(keyValue.getKey()); // we are done with this Division. Remove it from newDivision.
                    writeToFile(keyValue, sb, division, bufferedWriter);
                }
            }
            // now whatever remaining in the newDivision, just add to the file.
            for (Map.Entry<String, Division> keyValue : newDivision.entrySet()) {
                writeToFile(keyValue, sb, keyValue.getValue(), bufferedWriter);
            }
        }
    }

    private static void writeToFile(Map.Entry<String, Division> keyValue, StringBuilder sb, Division division, BufferedWriter bufferedWriter) throws IOException {
        sb.append(keyValue.getKey())
                .append(COMMA)
                .append(String.join(PIPE, division.getDepts()))
                .append(NEW_LINE);
        bufferedWriter.write(sb.toString());
        sb.setLength(0);
    }
}
