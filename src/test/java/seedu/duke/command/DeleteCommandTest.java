package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.PolicyList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DeleteCommandTest {

    private static class StubLookUpTable extends LookUpTable {
        private final Map<String, ListContainer> containers = new HashMap<>();

        public StubLookUpTable() {
            super(null, null, null, null, null, null);
        }

        public void addContainer(String key, ListContainer container) {
            containers.put(key, container);
        }

        @Override
        public ListContainer getList(String key) {
            return containers.get(key);
        }
    }


    private static class StubGenericListContainer implements ListContainer {
        private String argumentsPassedToDelete = null;

        @Override
        public void deleteItem(String arguments) {
            this.argumentsPassedToDelete = arguments;
        }
        // --- Other unused methods ---
        @Override public void addItem(String arguments) {}
        @Override public void addItem(String arguments, ListContainer listContainer) {}
        @Override public void listItems() {}
        @Override public int checkDeleteIndex(String arguments) { return 0; }
    }


    private static class StubPolicyList extends PolicyList {
        private String simpleDeleteArgs = null;
        private String overloadedDeleteArgs = null;
        private ClientList clientListPassed = null;

        @Override
        public void deleteItem(String arguments) {
            this.simpleDeleteArgs = arguments; // Should not be called by DeleteCommand
        }

        @Override
        public void deleteItem(String arguments, ClientList clientList) {
            this.overloadedDeleteArgs = arguments;
            this.clientListPassed = clientList;
        }
    }

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }



    @Test
    void execute_genericDelete_callsStandardDeleteItem() throws FinanceProPlusException {
        String subtype = "client";
        String arguments = "1";
        StubLookUpTable stubLookUpTable = new StubLookUpTable();
        StubGenericListContainer stubClientList = new StubGenericListContainer();
        stubLookUpTable.addContainer(subtype, stubClientList);
        DeleteCommand deleteCommand = new DeleteCommand(subtype, arguments);
        deleteCommand.execute(stubLookUpTable);
        assertEquals(arguments, stubClientList.argumentsPassedToDelete);
    }

    @Test
    void execute_policyDelete_callsOverloadedDeleteItem() throws FinanceProPlusException {
        String subtype = "policy";
        String arguments = "2";
        StubLookUpTable stubLookUpTable = new StubLookUpTable();
        StubPolicyList stubPolicyList = new StubPolicyList();
        ClientList stubClientList = new ClientList();
        stubLookUpTable.addContainer("policy", stubPolicyList);
        stubLookUpTable.addContainer("client", stubClientList);
        DeleteCommand deleteCommand = new DeleteCommand(subtype, arguments);
        deleteCommand.execute(stubLookUpTable);
        assertEquals(arguments, stubPolicyList.overloadedDeleteArgs);
        assertNotNull(stubPolicyList.clientListPassed, "A ClientList should have been passed.");
        assertSame(stubClientList, stubPolicyList.clientListPassed, "The correct ClientList instance should be passed.");
        assertNull(stubPolicyList.simpleDeleteArgs, "The standard deleteItem method should not have been called.");
    }

    @Test
    void printExecutionMessage_printsCorrectMessage() {
        DeleteCommand deleteCommand = new DeleteCommand("any", "any");
        deleteCommand.printExecutionMessage();
        assertEquals("----------------------------------------------------", outContent.toString().trim());
    }
}