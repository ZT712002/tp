package seedu.duke.command;

import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class Command {
    public Command(){

    }
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        throw new FinanceProPlusException("This command should be implemented by child classes");
    }
}
