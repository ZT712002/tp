package seedu.duke.command;

public class DeleteCommand extends Command{

    private String arguments;
    public DeleteCommand(String subtype, String args) {
        this.subtype = subtype;
        arguments = args;
    }

    @Override
    public void execute(seedu.duke.container.LookUpTable lookUpTable) throws seedu.duke.exception.FinanceProPlusException {
        seedu.duke.container.ListContainer listContainer = lookUpTable.getList(subtype);
        listContainer.deleteItem(arguments);
    }
}
