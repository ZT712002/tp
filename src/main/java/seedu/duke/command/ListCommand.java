package seedu.duke.command;

import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class ListCommand extends Command{
    private String arguments;

    public ListCommand(String subtype) {
        arguments = subtype;
    }
    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer listContainer = lookUpTable.getList(arguments);
        listContainer.listItems();
    }
    @Override
    public void printExecutionMessage() {

    }
}
