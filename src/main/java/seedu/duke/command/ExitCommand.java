package seedu.duke.command;

import seedu.duke.FinanceProPlus;
import seedu.duke.container.LookUpTable;

public class ExitCommand extends Command {
    public ExitCommand(){

    }
    @Override
    public void execute(LookUpTable lookUpTable) {
        FinanceProPlus.terminate();
    }
    @Override
    public void printExecutionMessage() {
        System.out.println("Exiting....");
    }
}
