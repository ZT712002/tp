package seedu.duke.command;


import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class AddCommand extends Command {
    private String arguments;

    public AddCommand(String subtype, String args) {
        this.subtype = subtype;
        arguments = args;
    }
    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ListContainer listContainer = lookUpTable.getList(subtype);
        if(subtype.equals("client")){
            ListContainer policyList = lookUpTable.getList("policy");
            listContainer.addItem(arguments, policyList);
            return;
        }
        listContainer.addItem(arguments);
    }
    @Override
    public void printExecutionMessage() {
        System.out.println("Added successfully!");
        System.out.println("----------------------------------------------------");
    }
}
