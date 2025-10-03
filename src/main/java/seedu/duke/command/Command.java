package seedu.duke.command;

import seedu.duke.exception.FinanceProPlusException;

public class Command {
    public Command(){

    }
    public void execute() throws FinanceProPlusException {
        throw new FinanceProPlusException("This command should be implemented by child classes");
    }
}
