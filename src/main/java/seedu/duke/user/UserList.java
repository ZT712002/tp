package seedu.duke.user;


import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

public class UserList implements ListContainer {
    private User user;

    public UserList() {
        this.user = null;
    }


    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        if (user != null) {
            throw new FinanceProPlusException("A user already exists. Delete the existing user before adding a new one.");
        }
        user = new User(arguments);
        System.out.println("Noted. I've added this user:");
        System.out.println(user.toString());
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if (user == null) {
            System.out.println("No user to delete.");
            return;
        }
        System.out.println("Noted. I've removed this user:");
        System.out.println(user.toString());
        user = null;
    }

    @Override
    public void listItems() throws FinanceProPlusException {
        if (user == null) {
            System.out.println("No user found.");
        } else {
            System.out.println("Here is the current user information:");
            System.out.println(user.toString());
        }
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {

        if (user == null) {
            throw new FinanceProPlusException("No user to delete.");
        }
        return 0;
    }
}