package seedu.duke;

import seedu.duke.client.ClientList;
import seedu.duke.lookuptable.LookUpTable;
import seedu.duke.meeting.MeetingList;
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

    public void run(){
        ui.printWelcomeMessage();

        while(runLoop){
            ui.setUserInput();
            String unprocessedInput = ui.getUserInput();

        }
    }
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new FinanceProPlus().run();
    }
}
