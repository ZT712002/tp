package seedu.duke;

import seedu.duke.client.ClientList;
import seedu.duke.command.Command;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.logger.LoggerConfig;
import seedu.duke.meeting.MeetingList;
import seedu.duke.parser.Parser;
import seedu.duke.policy.PolicyList;
import seedu.duke.user.UserList;
import seedu.duke.ui.Ui;
import seedu.duke.storage.StorageManager;




public class FinanceProPlus {
    private static boolean runLoop;
    private Ui ui;
    private StorageManager storage;
    private MeetingList meetings;
    private PolicyList policies;
    private ClientList clients;
    private LookUpTable lookUpTable;
    private UserList user;
    public FinanceProPlus() {
        ui = new Ui();
        runLoop = true;
        storage = new StorageManager();
        meetings = new MeetingList();
        policies = new PolicyList();
        clients = new ClientList();
        user = new UserList();
        LoggerConfig.setup();
        lookUpTable = new LookUpTable(clients, policies, meetings,user);
        try {
            policies.loadFromStorage(storage.loadFromFile("policy.txt"));
            clients.loadFromStorage(storage.loadFromFile("client.txt"),policies);
            user.loadFromStorage(storage.loadFromFile("user.txt"));
            meetings.loadFromStorage(storage.loadFromFile("meeting.txt"));
            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Some data failed to load.");
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
                Command c = Parser.parse(unprocessedInput);
                assert c != null : "Command should not be null";
                c.execute(lookUpTable);
                c.printExecutionMessage();

            } catch (FinanceProPlusException e) {
                System.out.println( e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                storage.saveToFile("user.txt", user.toStorageFormat());
                storage.exportToCSV("user.csv", user.toCSVFormat());
                storage.saveToFile("client.txt", clients.toStorageFormat());
                storage.exportToCSV("client.csv", clients.toCSVFormat());
                storage.saveToFile("policy.txt", policies.toStorageFormat());
                storage.exportToCSV("policy.csv", policies.toCSVFormat());
                storage.saveToFile("meeting.txt", meetings.toStorageFormat());
                storage.exportToCSV("meeting.csv", meetings.toCSVFormat());


            } catch (Exception e) {
                System.out.println("Error saving user data: " + e.getMessage());
            }
        }

        ui.closeScanner();
        ui.printGoodbyeMessage();

    }
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args)  {
        new FinanceProPlus().run();
    }
}
