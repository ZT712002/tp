package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.meeting.MeetingList;
import seedu.duke.policy.PolicyList;
import seedu.duke.task.TaskList;
import seedu.duke.user.UserList;
import seedu.duke.client.ArchivedClientList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListClientTodosCommandTest {

    static class SpyClient extends Client {
        boolean listTodosCalled = false;

        public SpyClient() throws FinanceProPlusException {
            super("n/Jane Doe c/87654321 id/S7654321B", new PolicyList());
        }

        @Override
        public void listTodos() throws FinanceProPlusException {
            listTodosCalled = true;
            super.listTodos();
        }
    }

    static class SpyClientList extends ClientList {
        boolean getClientByIdCalled = false;
        String receivedArgs;
        Client clientToReturn;
        boolean shouldThrow = false;

        public void setClientToReturn(Client client) {
            this.clientToReturn = client;
        }

        public void setShouldThrow(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public Client getClientByID(String args) throws FinanceProPlusException {
            getClientByIdCalled = true;
            receivedArgs = args;
            if (shouldThrow) {
                throw new FinanceProPlusException("Client not found.");
            }
            return clientToReturn;
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
    void execute_validArgs_listsTodosForClient() throws FinanceProPlusException {
        // add two todos first via Client API directly
        spyClient.addTodo("d/Task 1 by/31-12-2025");
        spyClient.addTodo("d/Task 2 by/31-12-2025");
        outContent.reset();

        ListClientTodosCommand command = new ListClientTodosCommand("client", "id/S7654321B");
        command.execute(lookUpTable);

        assertTrue(spyClientList.getClientByIdCalled, "getClientByID should be called.");
        assertEquals("id/S7654321B", spyClientList.receivedArgs, "Args should be passed to getClientByID.");
        assertTrue(spyClient.listTodosCalled, "listTodos should be called on the client.");

        String output = outContent.toString();
        assertTrue(output.contains("To-dos for client Jane Doe (NRIC: S7654321B):"));
        assertTrue(output.contains("Here are the tasks in your list:"));
        assertTrue(output.contains("1. Task 1"));
        assertTrue(output.contains("2. Task 2"));
    }

    @Test
    void execute_clientNotFound_throwsException() {
        spyClientList.setShouldThrow(true);
        ListClientTodosCommand command = new ListClientTodosCommand("client", "id/NOPE");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Client not found.", e.getMessage());
    }

    @Test
    void printExecutionMessage_printsSeparator() {
        ListClientTodosCommand command = new ListClientTodosCommand("client", "id/S7654321B");
        String expected = "----------------------------------------------------" + System.lineSeparator();
        command.printExecutionMessage();
        assertEquals(expected, outContent.toString());
    }
}


