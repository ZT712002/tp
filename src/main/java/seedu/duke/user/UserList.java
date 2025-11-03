package seedu.duke.user;


import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.logging.Logger;


import java.util.ArrayList;
import java.util.List;
/**
 * Represents a single-user container.
 * This class maintains at most one User instance at any time and provides methods to
 * add, edit, view, and serialize that user.
 */
public class UserList implements ListContainer {
    private static final Logger logger = Logger.getLogger(UserList.class.getName());
    private User user;

    public UserList() {
        this.user = null;
    }

    /**
     * Adds a user from CLI-style arguments (e.g., { n/John e/j@x.com c/98765432 r/FA-12345}).
     * If a user already exists, an exception is thrown.
     *
     * @param arguments serialized user fields.
     * @throws FinanceProPlusException if a user already exists or the arguments are invalid.
     */
    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty()
                : "Arguments for adding a user cannot be null or empty";
        if (user != null) {
            logger.warning("Attempted to add a new user when one already exists.");
            throw new FinanceProPlusException("A user already exists. Edit user to update information.");
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
        throw new FinanceProPlusException("Not implemented for UserList class");
    }

    @Override
    public void listItems(){
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
    /**
     * Returns a storage representation of the current user.
     *
     * @return a list containing one line if a user exists; otherwise an empty list.
     */
    public List<String> toStorageFormat() {
        List<String> lines = new ArrayList<>();
        if (user != null) {
            lines.add(user.toStorageString());
        }
        return lines;
    }
    /**
     * Loads the user from storage lines produced by {toStorageFormat()}.
     *
     * @param lines storage lines; may be empty.
     * @throws FinanceProPlusException if a line is malformed.
     */
    public void loadFromStorage(List<String> lines) throws FinanceProPlusException {
        if (lines.isEmpty()) {
            return;
        }
        user = new User(lines.get(0)); // only one user
    }

    public List<String[]> toCSVFormat() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Name", "Email", "Contact", "Representative"});
        if (user != null) {
            rows.add(user.toCSVRow());
        }
        return rows;
    }

    public boolean hasUser() {
        return user != null;
    }
    /**
     * Edits the current user using CLI-style arguments.
     *
     * @param arguments serialized user fields.
     * @throws FinanceProPlusException if the arguments are invalid.
     */
    public void editUser(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for edit cannot be null or empty";
        User newUser = new User(arguments);
        logger.info("User updated from: " + (user == null ? "None" : user.toString()) + " to: " + newUser.toString());
        user = newUser;
        System.out.println(user.toString());
    }

}
