package seedu.duke.command;

import seedu.duke.exception.FinanceProPlusException;

public class ListCommand extends Command{
    private String arguments;

    public ListCommand(String subtype) {
        arguments = subtype;
    }
    @Override
    public void execute(seedu.duke.container.LookUpTable lookUpTable) throws FinanceProPlusException {
        seedu.duke.container.ListContainer listContainer = lookUpTable.getList(arguments);
        listContainer.listItems();
    }
    @Override
    public void printExecutionMessage() {

    }
}
