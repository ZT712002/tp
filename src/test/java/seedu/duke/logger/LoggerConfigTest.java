package seedu.duke.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class LoggerConfigTest {

    private final Path logFilePath = Paths.get(LoggerConfig.LOG_FILE_PATH);
    @BeforeEach
    @AfterEach
    void cleanup() throws IOException {
        LogManager.getLogManager().reset();
        if (Files.isDirectory(logFilePath)) {
            Files.deleteIfExists(logFilePath);
        } else {
            Files.deleteIfExists(logFilePath);
        }
    }

    @Test
    void setup_successfulExecution_configuresLoggerCorrectly() {
        LoggerConfig.setup();
        Logger appLogger = Logger.getLogger(LoggerConfig.ROOT_NAME);
        assertFalse(appLogger.getUseParentHandlers(), "Parent handlers should be disabled.");
        assertEquals(Level.ALL, appLogger.getLevel(), "Logger level should be set to ALL.");
        assertEquals(1, appLogger.getHandlers().length, "There should be exactly" +
                " one handler attached.");
        assertTrue(appLogger.getHandlers()[0] instanceof FileHandler, "The handler should be a FileHandler.");
        FileHandler handler = (FileHandler) appLogger.getHandlers()[0];
        assertEquals(Level.ALL, handler.getLevel(), "Handler level should be set to ALL.");
        assertTrue(handler.getFormatter() instanceof SimpleFormatter, "Handler's formatter should be" +
                " a SimpleFormatter.");
    }

    @Test
    void setup_whenIOExceptionOccurs_doesNotAddFileHandler() throws IOException {
        Files.createDirectory(logFilePath);
        LoggerConfig.setup();
        Logger appLogger = Logger.getLogger(LoggerConfig.ROOT_NAME);
        assertEquals(0, appLogger.getHandlers().length, "No handlers should be added if " +
                "FileHandler creation fails.");
    }
}