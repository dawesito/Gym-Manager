package es.upm.pproject.gym.infrastructure.implementations;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PersistenceManager {

    private PersistenceManager() {
        /* This utility class should not be instantiated */
    }

    private static final Logger logger = LoggerFactory.getLogger(PersistenceManager.class);
    private static final String PERSISTENCE_PATH = "persistence/";

    public static List<String[]> readCSV(String fileName) {
        List<String[]> data = new ArrayList<>();
        Path path = Paths.get(PERSISTENCE_PATH + fileName);
        if (!Files.exists(path)) {
            logger.debug("File {} does not exist, returning empty list", fileName);
            return data;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.split(";"));
                }
            }
            logger.debug("Successfully read {} lines from {}", data.size(), fileName);
        } catch (IOException e) {
            logger.error("Error reading file: {} - {}", fileName, e.getMessage());
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
            logger.debug("Successfully wrote {} lines to {}", data.size(), fileName);
        } catch (IOException e) {
            logger.error("Error writing file: {} - {}", fileName, e.getMessage());
        }
    }
}
