package seedu.duke.command;

import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.user.UserList;

public class EditUserCommand extends Command {
    private final String arguments;

    public EditUserCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        UserList userList = (UserList) lookUpTable.getList(subtype);
        assert userList != null : "User list should not be null";

        if (!userList.hasUser()) {
            System.out.println("No existing user found. Adding new user instead...");
            userList.addItem(arguments);
            return;
        }
        System.out.println("Updating user details...");
        userList.editUser(arguments);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
        System.out.println("User details successfully updated.");
        System.out.println("----------------------------------------------------");
    }
}

