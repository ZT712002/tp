package seedu.duke.container;

import seedu.duke.exception.FinanceProPlusException;
/*
 * An interface to represent a container that can list, add and delete items.
 */
public interface ListContainer {
    void addItem(String arguments) throws FinanceProPlusException;
    void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException;

    void deleteItem(String arguments) throws FinanceProPlusException;

    void listItems() throws FinanceProPlusException;

    int checkDeleteIndex(String arguments) throws FinanceProPlusException;
}
