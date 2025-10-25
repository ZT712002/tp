package seedu.duke.command;

import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class UpdateClientPolicyCommand extends UpdateCommand {
    private String arguments;

    public UpdateClientPolicyCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    /**
     * Executes the command to update an existing policy for a client.
     * @param lookUpTable The LookUpTable containing all data lists.
     * @throws FinanceProPlusException If any validation fails (e.g., client or policy not found).
     */
    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer clientListContainer = lookUpTable.getList(subtype);
        if (!(clientListContainer instanceof ClientList)) {
            throw new FinanceProPlusException("Internal Error: 'client' list is not a valid ClientList.");
        }
        ClientList clientList = (ClientList) clientListContainer;
        clientList.updatePolicyForClient(arguments);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("Policy updated successfully!");
        System.out.println("----------------------------------------------------");
    }
}
