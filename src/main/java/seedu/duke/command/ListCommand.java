package seedu.duke.command;

public class ListCommand extends Command{
    private String arguments;

    public ListCommand(String subtype) {
        arguments = subtype;
    }
    @Override
    public void execute(seedu.duke.container.LookUpTable lookUpTable) throws seedu.duke.exception.FinanceProPlusException {
        seedu.duke.container.ListContainer listContainer = lookUpTable.getList(arguments);
        listContainer.listItems();
    }
    @Override
    public void printExecutionMessage() {

    }
}
