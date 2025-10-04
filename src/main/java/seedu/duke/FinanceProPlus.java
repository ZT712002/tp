package seedu.duke;

import seedu.duke.client.ClientList;
import seedu.duke.command.Command;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.parser.Parser;
import seedu.duke.policy.PolicyList;
import seedu.duke.ui.Ui;



public class FinanceProPlus {
    private Ui ui;
    private static boolean runLoop;
    private MeetingList meetings;
    private PolicyList policies;
    private ClientList clients;
    private LookUpTable lookUpTable;
    public FinanceProPlus() {
        ui = new Ui();
        runLoop = true;
        meetings = new MeetingList();
        policies = new PolicyList();
        clients = new ClientList();
        lookUpTable = new LookUpTable(clients, policies, meetings);
    }

    public static void terminate() {
        runLoop = false;
    }

    public void run() {
        ui.printWelcomeMessage();

        while(runLoop){
            try {
                ui.setUserInput();
                String unprocessedInput = ui.getUserInput();
                Command c = Parser.parse(unprocessedInput);
                c.execute(lookUpTable);
                c.printExecutionMessage();
            }
            catch (FinanceProPlusException e) {
                System.out.println(e.getMessage());
            }

        }
        ui.printGoodbyeMessage();
    }
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) throws FinanceProPlusException {
        new FinanceProPlus().run();
    }
}
