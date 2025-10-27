package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.ArchivedClientList;
import seedu.duke.client.ClientList;
import seedu.duke.container.ListContainer;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.user.UserList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AddCommandTest {

    static class SpyClientList extends ClientList {
        boolean addItemWithOneArgCalled = false;
        boolean addItemWithTwoArgsCalled = false;
        String receivedStringArg;
        ListContainer receivedListContainerArg;
        boolean shouldThrow = false;


        public SpyClientList() {
            super();
        }

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }


        @Override
        public void addItem(String arguments) throws FinanceProPlusException {
            if (shouldThrow) {
                throw new FinanceProPlusException("Test exception from one-arg addItem");
            }
            this.addItemWithOneArgCalled = true;
            this.receivedStringArg = arguments;
        }

        // Override the two-argument method to record the call
        @Override
        public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
            if (shouldThrow) {
                throw new FinanceProPlusException("Test exception from two-arg addItem");
            }
            this.addItemWithTwoArgsCalled = true;
            this.receivedStringArg = arguments;
            this.receivedListContainerArg = listContainer;
        }
    }


    static class SpyPolicyList extends PolicyList {
        boolean addItemWithOneArgCalled = false;
        boolean addItemWithTwoArgsCalled = false;
        String receivedStringArg;
        boolean shouldThrow = false;

        public SpyPolicyList() {
            super();
        }

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public void addItem(String arguments) throws FinanceProPlusException {
            if (shouldThrow) {
                throw new FinanceProPlusException("Invalid policy details");
            }
            this.addItemWithOneArgCalled = true;
            this.receivedStringArg = arguments;
        }

        @Override
        public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
            // This method shouldn't be called for PolicyList in our test cases
            this.addItemWithTwoArgsCalled = true;
        }
    }
    static class StubMeetingList extends MeetingList {}
    static class StubUserList extends UserList {}
    static class StubArchivedClientList extends ArchivedClientList{}
    private LookUpTable lookUpTable;
    private SpyClientList spyClientList;
    private SpyPolicyList spyPolicyList;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        spyClientList = new SpyClientList();
        spyPolicyList = new SpyPolicyList();
        StubMeetingList stubMeetingList = new StubMeetingList();
        StubUserList stubUserList = new StubUserList();


        lookUpTable = new LookUpTable(spyClientList, spyPolicyList, stubMeetingList, stubUserList,
                new StubArchivedClientList());


        System.setOut(new PrintStream(outContent));

    }
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_addClientSubtype_callsAddItemWithTwoArguments() throws FinanceProPlusException {
        String clientArgs = "n/John Doe c/123 id/S123A";
        AddCommand command = new AddCommand("client", clientArgs);
        command.execute(lookUpTable);
        assertTrue(spyClientList.addItemWithTwoArgsCalled, "The two-argument addItem on ClientList should " +
                "be called.");
        assertFalse(spyClientList.addItemWithOneArgCalled, "The one-argument addItem on ClientList should " +
                "NOT be called.");
        assertEquals(clientArgs, spyClientList.receivedStringArg);
        assertSame(spyPolicyList, spyClientList.receivedListContainerArg, "The policy list passed should be" +
                " the correct instance.");
        assertFalse(spyPolicyList.addItemWithOneArgCalled, "addItem on PolicyList should not be called.");
    }

    @Test
    void execute_addPolicySubtype_callsAddItemWithOneArgument() throws FinanceProPlusException {
        String policyArgs = "n/HealthGuard p/1200 t/500 a/60";
        AddCommand command = new AddCommand("policy", policyArgs);
        command.execute(lookUpTable);
        assertTrue(spyPolicyList.addItemWithOneArgCalled, "The one-argument addItem on PolicyList should " +
                "be called.");
        assertEquals(policyArgs, spyPolicyList.receivedStringArg);
        assertFalse(spyClientList.addItemWithOneArgCalled, "addItem on ClientList should not be called.");
        assertFalse(spyClientList.addItemWithTwoArgsCalled, "addItem on ClientList should not be called.");
    }

    @Test
    void execute_listContainerThrowsException_propagatesException() {
        String invalidArgs = "some invalid arguments";
        AddCommand command = new AddCommand("policy", invalidArgs);
        spyPolicyList.setShouldThrow(true);
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Invalid policy details", e.getMessage());
    }


    @Test
    void execute_invalidSubtype_returnsNullAndDoesNotCrash() {
        AddCommand command = new AddCommand("invalidSubtype", "some args");

        assertThrows(NullPointerException.class, () -> command.execute(lookUpTable));
    }

    @Test
    void printExecutionMessage_printsCorrectMessageToConsole() {
        AddCommand command = new AddCommand("client", "some args");
        String expectedOutput = "Added successfully!" + System.lineSeparator() +
                "----------------------------------------------------" + System.lineSeparator();
        command.printExecutionMessage();
        assertEquals(expectedOutput, outContent.toString());
    }
}

