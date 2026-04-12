package es.upm.pproject.gym.infrastructure.implementations;

import java.io.*;
import java.nio.file.*;
import java.util.*;

class PersistenceManager {

    private static final String PERSISTENCE_PATH = "persistence/";

    public static List<String[]> readCSV(String fileName) {
        List<String[]> data = new ArrayList<>();
        Path path = Paths.get(PERSISTENCE_PATH + fileName);
        if (!Files.exists(path)) {
            return data;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.split(";"));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName + " - " + e.getMessage());
        }
        return data;
    }

    public static void writeCSV(String fileName, List<String[]> data) {
        Path path = Paths.get(PERSISTENCE_PATH + fileName);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (String[] row : data) {
                bw.write(String.join(";", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + fileName + " - " + e.getMessage());
        }
    }
}
