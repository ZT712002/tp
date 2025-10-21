package seedu.duke.command;

import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class AddPolicyCommand extends Command {
    private String arguments;

    public AddPolicyCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    /**
     * Executes the command to add a policy to an existing client.
     * @param lookUpTable The LookUpTable containing all data lists.
     * @throws FinanceProPlusException If any validation fails (e.g., client not found).
     */
    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer clientListContainer = lookUpTable.getList(subtype);
        ListContainer policyListContainer = lookUpTable.getList("policy");
        if (!(clientListContainer instanceof ClientList)) {
            throw new FinanceProPlusException("Internal Error: 'client' list is not a ClientList.");
        }
        ClientList clientList = (ClientList) clientListContainer;
        clientList.addPolicyToClient(arguments, policyListContainer);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("Policy/Policies added successfully to the client!");
        System.out.println("----------------------------------------------------");
    }
}