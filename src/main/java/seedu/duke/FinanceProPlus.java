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




public class FinanceProPlus {
    private static boolean runLoop;
    private Ui ui;
    private MeetingList meetings;
    private PolicyList policies;
    private ClientList clients;
    private LookUpTable lookUpTable;
    private UserList user;
    public FinanceProPlus() {
        ui = new Ui();
        runLoop = true;
        meetings = new MeetingList();
        policies = new PolicyList();
        clients = new ClientList();
        user = new UserList();
        LoggerConfig.setup();
        lookUpTable = new LookUpTable(clients, policies, meetings,user);
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
