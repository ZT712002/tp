package seedu.duke.command;

import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;


public class DeleteClientPolicyCommand extends Command {
    private String arguments;

    public DeleteClientPolicyCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer clientListContainer = lookUpTable.getList(subtype);

        if (!(clientListContainer instanceof ClientList)) {
            throw new FinanceProPlusException("Internal Error: 'client' list is not a valid ClientList.");
        }
        ClientList clientList = (ClientList) clientListContainer;
        clientList.deletePolicyForClient(arguments);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}