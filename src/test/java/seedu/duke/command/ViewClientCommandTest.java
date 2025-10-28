package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.client.ArchivedClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;
import seedu.duke.user.UserList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewClientCommandTest {

    static class SpyClient extends Client {
        boolean viewDetailsCalled = false;


        public SpyClient() throws FinanceProPlusException {
            super("n/dummy c/123 id/DUMMY", new PolicyList());
        }

        @Override
        public void viewDetails() {
            this.viewDetailsCalled = true;
        }
    }


    static class SpyClientList extends ClientList {
        boolean getClientByIdCalled = false;
        String receivedArgs;
        Client clientToReturn; // The spy will return this object
        boolean shouldThrow = false;

        public void setClientToReturn(Client client) {
            this.clientToReturn = client;
        }

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public Client getClientByID(String args) throws FinanceProPlusException {
            this.getClientByIdCalled = true;
            this.receivedArgs = args;
            if (shouldThrow) {
                throw new FinanceProPlusException("Error: Client not found");
            }
            return this.clientToReturn;
        }
    }


    static class StubPolicyList extends PolicyList {}
    static class StubMeetingList extends MeetingList {}
    static class StubTaskList extends TaskList {}
    static class StubUserList extends UserList {}
    static class StubArchivedClientList extends ArchivedClientList {}

    private LookUpTable lookUpTable;
    private SpyClientList spyClientList;
    private SpyClient spyClient;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws FinanceProPlusException {
        spyClientList = new SpyClientList();
        spyClient = new SpyClient();
        spyClientList.setClientToReturn(spyClient);

        lookUpTable = new LookUpTable(spyClientList, new StubPolicyList(), new StubMeetingList(),
                new StubTaskList(), new StubUserList(), new StubArchivedClientList());

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void execute_clientFound_callsViewDetailsOnClient() throws FinanceProPlusException {
        String args = "id/S123A";
        ViewClientCommand command = new ViewClientCommand("client", args);
        command.execute(lookUpTable);
        assertTrue(spyClientList.getClientByIdCalled, "getClientByID should have been called on the list.");
        assertEquals(args, spyClientList.receivedArgs, "The arguments should be passed to getClientByID.");
        assertTrue(spyClient.viewDetailsCalled, "viewDetails() should have been called on the found client.");
    }

    @Test
    void execute_clientNotFound_propagatesException() {
        String args = "id/NONEXISTENT";
        ViewClientCommand command = new ViewClientCommand("client", args);
        spyClientList.setShouldThrow(true);
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class, () -> command.execute(lookUpTable));
        assertEquals("Error: Client not found", e.getMessage());
        assertFalse(spyClient.viewDetailsCalled, "viewDetails() should not be called if the client is not found.");
    }

    @Test
    void execute_wrongSubtype_throwsClassCastException() {
        ViewClientCommand command = new ViewClientCommand("policy", "some args");
        assertThrows(ClassCastException.class, () -> command.execute(lookUpTable));
    }

    @Test
    void execute_invalidSubtype_throwsAssertionErrorIfEnabled() {
        ViewClientCommand command = new ViewClientCommand("invalid", "some args");
        assertThrows(AssertionError.class, () -> command.execute(lookUpTable));
    }

    @Test
    void printExecutionMessage_printsCorrectly() {
        ViewClientCommand command = new ViewClientCommand("client", "");
        String expected = "----------------------------------------------------" + System.lineSeparator();
        command.printExecutionMessage();
        assertEquals(expected, outContent.toString());
    }
}
