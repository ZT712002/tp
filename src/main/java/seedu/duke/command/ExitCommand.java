package seedu.duke.command;

import seedu.duke.FinanceProPlus;

public class ExitCommand extends Command {
    public ExitCommand(){

    }
    @Override
    public void execute(){
        FinanceProPlus.terminate();
    }
}
