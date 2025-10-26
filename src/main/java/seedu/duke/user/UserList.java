package seedu.duke.user;


import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.logging.Logger;


import java.util.ArrayList;
import java.util.List;

public class UserList implements ListContainer {
    private static final Logger logger = Logger.getLogger(UserList.class.getName());
    private User user;

    public UserList() {
        this.user = null;
    }


    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty()
                : "Arguments for adding a user cannot be null or empty";
        if (user != null) {
            logger.warning("Attempted to add a new user when one already exists.");
            throw new FinanceProPlusException("A user already exists. Delete user before adding a new one.");
        }
        user = new User(arguments);
        logger.info("User added: " + user.toString());
        assert user != null : "User object should have been successfully created";
        System.out.println("Noted. I've added this user:");
        System.out.println(user.toString());
    }

    @Override
    public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
        throw new FinanceProPlusException("Implemented only on ClientList class");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if (user == null) {
            System.out.println("No user to delete.");
            return;
        }
        logger.info("User deleted: " + user.toString());
        System.out.println("Noted. I've removed this user:");
        System.out.println(user.toString());
        user = null;
        assert user == null : "User should be null after deletion";
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
            logger.warning("Delete index check failed: no user exists.");
            throw new FinanceProPlusException("No user to delete.");
        }

        return 0;
    }

    // ========== STORAGE HELPERS ==========
    public List<String> toStorageFormat() {
        List<String> lines = new ArrayList<>();
        if (user != null) {
            lines.add(user.toStorageString());
        }
        return lines;
    }

    public void loadFromStorage(List<String> lines) throws FinanceProPlusException {
        if (lines.isEmpty()){ return;}
        user = new User(lines.get(0)); // only one user
    }

    public List<String[]> toCSVFormat() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Name", "Email", "Phone", "Representative"});
        if (user != null) {rows.add(user.toCSVRow());}
        return rows;
    }

}
