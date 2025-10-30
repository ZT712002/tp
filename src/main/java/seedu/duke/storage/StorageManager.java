package seedu.duke.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading of program data to disk.
 * Supports both text storage (for internal persistence) and CSV export (for Excel viewing).
 */
public class StorageManager {

    private static final String DATA_FOLDER = "data/";
    private static final String CLIENT_TASKS_FOLDER = "data/client_tasks/";
    private static final String EXPORT_FOLDER = "exports/";

    public StorageManager() {
        createFolder(DATA_FOLDER);
        createFolder(EXPORT_FOLDER);
        createFolder(CLIENT_TASKS_FOLDER);
    }

    private void createFolder(String folder) {
        File directory = new File(folder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveToFile(String filename, List<String> lines) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        if (lines == null) {
            throw new IllegalArgumentException("Lines cannot be null");
        }

        File file = new File(DATA_FOLDER + filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    public List<String> loadFromFile(String filename) throws IOException {
        File file = new File(DATA_FOLDER + filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return Files.readAllLines(Path.of(DATA_FOLDER + filename));
    }


    public void exportToCSV(String filename, List<String[]> rows) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("CSV filename cannot be null or empty");
        }
        if (rows == null) {
            throw new IllegalArgumentException("CSV rows cannot be null");
        }

        File file = new File(EXPORT_FOLDER + filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            for (String[] row : rows) {
                writer.write(String.join(",", escapeCsv(row)));
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    private String[] escapeCsv(String[] fields) {
        String[] escaped = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (field.contains(",") || field.contains("\"")) {
                field = "\"" + field.replace("\"", "\"\"") + "\"";
            }
            escaped[i] = field;
        }
        return escaped;
    }

    public void saveClientTasks(String nric, List<String> lines) throws IOException {
        if (nric == null || nric.isEmpty()) {
            throw new IllegalArgumentException("NRIC cannot be null or empty");
        }
        saveToFile("client_tasks/" + nric + ".txt", lines);
    }

    public List<String> loadClientTasks(String nric) throws IOException {
        if (nric == null || nric.isEmpty()) {
            return new ArrayList<>();
        }
        File file = new File(DATA_FOLDER + "client_tasks/" + nric + ".txt");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return Files.readAllLines(Path.of(file.getPath()));
    }

}
