package seedu.duke.container;

import seedu.duke.client.Client;
import seedu.duke.exception.FinanceProPlusException;

public interface ListContainer {
    void addItem(String arguments) throws FinanceProPlusException;

    void deleteItem(String arguments);
}
