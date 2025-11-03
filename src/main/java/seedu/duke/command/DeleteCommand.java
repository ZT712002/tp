package seedu.duke.command;

import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.PolicyList;

public class DeleteCommand extends Command{

    private String arguments;
    public DeleteCommand(String subtype, String args) {
        this.subtype = subtype;
        arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        if (subtype.equals("policy")) {
            PolicyList policyList = (PolicyList) lookUpTable.getList("policy");
            ClientList clientList = (ClientList) lookUpTable.getList("client");
            policyList.deleteItem(arguments, clientList);
        } else {
            ListContainer listContainer = lookUpTable.getList(subtype);
            listContainer.deleteItem(arguments);
        }
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
