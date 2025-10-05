package seedu.duke.command;

import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class DeleteCommand extends Command{

    private String arguments;
    public DeleteCommand(String subtype, String args) {
        this.subtype = subtype;
        arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer listContainer = lookUpTable.getList(subtype);
        listContainer.deleteItem(arguments);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
