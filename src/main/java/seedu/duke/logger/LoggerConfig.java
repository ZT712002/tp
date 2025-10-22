package seedu.duke.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {

    /**
     * Initializes and configures the root logger for the application.
     * This method should be called once at the start of the application.
     */
    static final String LOG_FILE_PATH = "financeproplus.log";
    static final String ROOT_NAME = "seedu.duke";
    public static void setup() {
        Logger appLogger = Logger.getLogger(ROOT_NAME);
        appLogger.setUseParentHandlers(false);

        try {
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            appLogger.addHandler(fileHandler);
            appLogger.setLevel(Level.ALL);

            Logger.getLogger("seedu.duke").info("Logging has been set up successfully.");

        } catch (IOException e) {
            Logger.getLogger(LoggerConfig.class.getName())
                    .log(Level.SEVERE, "Error setting up logger.", e);
        }
    }
}
