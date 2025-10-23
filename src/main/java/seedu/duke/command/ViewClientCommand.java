package seedu.duke.command;

import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class ViewClientCommand extends ViewCommand{
    private String args;
    public ViewClientCommand(String commandType, String args) {
        subtype = commandType;
        this.args = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ClientList listContainer = (ClientList) lookUpTable.getList(subtype);
        assert listContainer != null;
        assert listContainer instanceof ClientList : "Something wrong with ViewClientCommand execute";
        Client client = listContainer.getClientByID(args);
        client.viewDetails();
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
