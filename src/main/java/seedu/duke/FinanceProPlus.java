package seedu.duke;

import seedu.duke.ui.Ui;



public class FinanceProPlus {
    private Ui ui;
    public FinanceProPlus() {
        ui = new Ui();
    }
    public void run(){
        ui.printWelcomeMessage();
        boolean runLoop = ui.getIsActive();
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
