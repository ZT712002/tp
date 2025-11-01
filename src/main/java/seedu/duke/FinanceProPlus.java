package seedu.duke;

import seedu.duke.client.ArchivedClientList;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.command.Command;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.logger.LoggerConfig;
import seedu.duke.meeting.MeetingList;
import seedu.duke.parser.Parser;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;
import seedu.duke.user.UserList;
import seedu.duke.ui.Ui;
import seedu.duke.storage.StorageManager;

import java.util.List;
import java.util.logging.Logger;

public class FinanceProPlus {
    private static boolean runLoop;
    private static final Logger logger = Logger.getLogger(FinanceProPlus.class.getName());
    private Ui ui;
    private StorageManager storage;
    private MeetingList meetings;
    private PolicyList policies;
    private ClientList clients;
    private TaskList tasks;
    private ArchivedClientList archivedClients;
    private LookUpTable lookUpTable;
    private UserList user;

    public FinanceProPlus() {
        ui = new Ui();
        runLoop = true;
        storage = new StorageManager();
        meetings = new MeetingList();
        policies = new PolicyList();
        clients = new ClientList();
        tasks = new TaskList();
        user = new UserList();
        archivedClients = new ArchivedClientList();
        LoggerConfig.setup();
        lookUpTable = new LookUpTable(clients, policies, meetings, tasks, user, archivedClients);
        loadFromFiles();
    }

    private void loadFromFiles() {
        try {
            policies.loadFromStorage(storage.loadFromFile("policy.txt"));
            clients.loadFromStorage(storage.loadFromFile("client.txt"), policies);
            user.loadFromStorage(storage.loadFromFile("user.txt"));
            meetings.loadFromStorage(storage.loadFromFile("meeting.txt"));
            archivedClients.loadFromStorage(storage.loadFromFile("archived_clients.txt"), policies);
            for (Client c : clients.getClientList()) {
                try {
                    List<String> taskLines = storage.loadClientTasks(c.getNric());
                    c.getTodoList().loadFromStorage(taskLines);
                } catch (Exception ex) {
                    logger.warning("Failed to load tasks for client " + c.getNric() + ": " + ex.getMessage());
                }
            }
            tasks.loadFromStorage(storage.loadFromFile("task.txt"));
            logger.info("Data loaded successfully.");
        } catch (Exception e) {
            logger.info("Some data failed to load: " + e.getMessage());
        }
    }

    public static void terminate() {
        runLoop = false;
    }

    public void run() {
        ui.printWelcomeMessage();

        while (this.runLoop) {
            try {
                String unprocessedInput = ui.readCommand();
                if (unprocessedInput.equalsIgnoreCase("exit")) {
                    this.terminate();
                    continue;
                }
                Command c = Parser.parse(unprocessedInput.trim());
                assert c != null : "Command should not be null";
                c.execute(lookUpTable);
                c.printExecutionMessage();

            } catch (FinanceProPlusException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            saveAllData();
        }

        ui.closeScanner();
        ui.printGoodbyeMessage();

    }

    private void saveAllData() {
        try {
            storage.saveToFile("user.txt", user.toStorageFormat());
            storage.exportToCSV("user.csv", user.toCSVFormat());
            storage.saveToFile("client.txt", clients.toStorageFormat());
            storage.exportToCSV("client.csv", clients.toCSVFormat());
            storage.saveToFile("policy.txt", policies.toStorageFormat());
            storage.exportToCSV("policy.csv", policies.toCSVFormat());
            storage.saveToFile("meeting.txt", meetings.toStorageFormat());
            storage.exportToCSV("meeting.csv", meetings.toCSVFormat());
            storage.saveToFile("archived_clients.txt", archivedClients.toStorageFormat());
            storage.exportToCSV("archived_clients.csv", archivedClients.toCSVFormat());
            storage.saveToFile("task.txt", tasks.toStorageFormat());
            storage.exportToCSV("task.csv", tasks.toCSVFormat());
            for (Client c : clients.getClientList()) {
                try {
                    storage.saveClientTasks(c.getNric(), c.getTodoList().toStorageFormat());
                } catch (Exception ex) {
                    logger.warning("Failed to save tasks for client " + c.getNric() + ": " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            logger.info("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new FinanceProPlus().run();
    }
}
