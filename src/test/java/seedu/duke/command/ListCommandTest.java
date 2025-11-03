package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class ListCommandTest {

    private static class StubListContainer implements ListContainer {
        private boolean listItemsWasCalled = false;

        public boolean wasListItemsCalled() {
            return listItemsWasCalled;
        }

        @Override
        public void listItems() {
            this.listItemsWasCalled = true;
        }

        @Override
        public void addItem(String arguments) {  }
        @Override
        public void addItem(String arguments, ListContainer listContainer) {  }
        @Override
        public void deleteItem(String arguments) {  }
        @Override
        public int checkDeleteIndex(String arguments) {
            return 0;
        }
    }


    private static class StubLookUpTable extends LookUpTable {
        private final ListContainer containerToReturn;
        private final String expectedKey;
        private String actualKeyCalled = null;


        public StubLookUpTable(String expectedKey, ListContainer containerToReturn) {
            super(null, null, null, null, null, null);
            this.expectedKey = expectedKey;
            this.containerToReturn = containerToReturn;
        }

        @Override
        public ListContainer getList(String key) {
            this.actualKeyCalled = key;
            if (key.equals(expectedKey)) {
                return containerToReturn;
            }
            fail("StubLookUpTable was called with an unexpected key: " + key);
            return null;
        }
    }


    @Test
    void execute_listCommand_correctlyInteractsWithDependencies() throws FinanceProPlusException {
        String subtype = "client";
        StubListContainer stubContainer = new StubListContainer();
        StubLookUpTable stubLookUpTable = new StubLookUpTable(subtype, stubContainer);
        ListCommand listCommand = new ListCommand(subtype);
        listCommand.execute(stubLookUpTable);
        assertEquals(subtype, stubLookUpTable.actualKeyCalled,
                "The command should call getList() with the subtype it was constructed with.");
        assertTrue(stubContainer.wasListItemsCalled(),
                "The command should call the listItems() method on the container.");
    }

    @Test
    void printExecutionMessage_emptyMethod_runsWithoutError() {
        ListCommand listCommand = new ListCommand("anySubtype");
        listCommand.printExecutionMessage();
    }
}
