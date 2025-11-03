package seedu.duke.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StorageManagerTest {

    private StorageManager storageManager;
    private final String testDataFolder = "test_data/";
    private final String testClientTasksFolder = "test_data/client_tasks/";
    private final String testClientPoliciesFolder = "test_data/client_policies/";
    private final String testExportsFolder = "test_exports/";

    @BeforeEach
    void setUp() {
        new File(testDataFolder).mkdirs();
        new File(testClientTasksFolder).mkdirs();
        new File(testClientPoliciesFolder).mkdirs();
        new File(testExportsFolder).mkdirs();

        storageManager = new StorageManager();
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteRecursively(new File(testDataFolder));
        deleteRecursively(new File(testExportsFolder));
    }

    private void deleteRecursively(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File sub : file.listFiles()) {
                    deleteRecursively(sub);
                }
            }
            Files.deleteIfExists(file.toPath());
        }
    }


    @Test
    void saveAndLoadFile_success() throws IOException {
        List<String> lines = List.of("line1", "line2", "line3");
        storageManager.saveToFile("test.txt", lines);

        List<String> loaded = storageManager.loadFromFile("test.txt");
        assertEquals(lines, loaded);
    }

    @Test
    void saveToFile_nullFilename_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.saveToFile(null, List.of("x")));
    }

    @Test
    void saveToFile_nullLines_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.saveToFile("file.txt", null));
    }

    @Test
    void loadFromFile_nonexistentFile_returnsEmptyList() throws IOException {
        List<String> result = storageManager.loadFromFile("does_not_exist.txt");
        assertTrue(result.isEmpty());
    }


    @Test
    void exportToCSV_success() throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Header1", "Header2"});
        rows.add(new String[]{"Value1", "Value2"});
        storageManager.exportToCSV("export.csv", rows);

        File csvFile = new File("exports/export.csv");
        assertTrue(csvFile.exists());

        List<String> lines = Files.readAllLines(csvFile.toPath());
        assertTrue(lines.get(0).contains("Header1"));
        assertTrue(lines.get(1).contains("Value1"));
    }

    @Test
    void exportToCSV_withCommasAndQuotes_escapesProperly() throws IOException {
        List<String[]> rows = List.of(
                new String[]{"Name", "Description"},
                new String[]{"Item1", "Contains,comma"},
                new String[]{"Item2", "Quote\"Here"}
        );
        storageManager.exportToCSV("escaped.csv", rows);

        List<String> lines = Files.readAllLines(new File("exports/escaped.csv").toPath());
        assertTrue(lines.get(1).contains("\"Contains,comma\""));
        assertTrue(lines.get(2).contains("\"Quote\"\"Here\""));
    }

    @Test
    void exportToCSV_nullFilename_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.exportToCSV(null, List.of()));
    }

    @Test
    void exportToCSV_nullRows_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.exportToCSV("file.csv", null));
    }


    @Test
    void saveAndLoadClientTasks_success() throws IOException {
        String nric = "S1234567A";
        List<String> tasks = List.of("Task 1", "Task 2");
        storageManager.saveClientTasks(nric, tasks);

        List<String> loaded = storageManager.loadClientTasks(nric);
        assertEquals(tasks, loaded);
    }

    @Test
    void saveClientTasks_invalidNric_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.saveClientTasks("", List.of("Task")));
    }

    @Test
    void loadClientTasks_nonexistentFile_returnsEmptyList() throws IOException {
        List<String> loaded = storageManager.loadClientTasks("S9999999Z");
        assertTrue(loaded.isEmpty());
    }


    @Test
    void saveAndLoadClientPolicies_success() throws IOException {
        String nric = "S7654321B";
        List<String> policies = List.of("p/HealthSecure m/200 s/2024-01-01 e/2026-01-01");
        storageManager.saveClientPolicies(nric, policies);

        List<String> loaded = storageManager.loadClientPolicies(nric);
        assertEquals(policies, loaded);
    }

    @Test
    void saveClientPolicies_invalidNric_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> storageManager.saveClientPolicies("", List.of("Policy")));
    }

    @Test
    void loadClientPolicies_nonexistent_returnsEmptyList() throws IOException {
        List<String> loaded = storageManager.loadClientPolicies("T0000000X");
        assertTrue(loaded.isEmpty());
    }
    // --- loadClientPolicies & loadClientTasks null/empty NRIC paths ---

    @Test
    void loadClientPolicies_nullNric_returnsEmptyList() throws IOException {
        List<String> result = storageManager.loadClientPolicies(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void loadClientPolicies_emptyNric_returnsEmptyList() throws IOException {
        List<String> result = storageManager.loadClientPolicies("");
        assertTrue(result.isEmpty());
    }

    @Test
    void loadClientTasks_nullNric_returnsEmptyList() throws IOException {
        List<String> result = storageManager.loadClientTasks(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void loadClientTasks_emptyNric_returnsEmptyList() throws IOException {
        List<String> result = storageManager.loadClientTasks("");
        assertTrue(result.isEmpty());
    }
}

