package seedu.duke.command;

import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class ListClientTodosCommand extends Command {
    private String arguments;

    public ListClientTodosCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ClientList clientList = (ClientList) lookUpTable.getList(subtype);
        assert clientList != null : "Client list should not be null";

        // Get the client by NRIC
        Client client = clientList.getClientByID(arguments);
        if (client == null) {
            throw new FinanceProPlusException("Client not found.");
        }

        client.listTodos();
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}


