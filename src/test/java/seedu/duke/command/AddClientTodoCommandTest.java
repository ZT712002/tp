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

class AddClientTodoCommandTest {

    static class SpyClient extends Client {
        boolean addTodoCalled = false;

        public SpyClient() throws FinanceProPlusException {
            super("n/John Doe c/12345678 id/S1234567A", new PolicyList());
        }

        @Override
        public void addTodo(String todoArguments) throws FinanceProPlusException {
            addTodoCalled = true;
            super.addTodo(todoArguments);
        }
    }

    static class SpyClientList extends ClientList {
        Client clientToReturn;
        boolean findByNricCalled = false;
        String receivedNric;
        boolean shouldReturnNull = false;

        public void setClientToReturn(Client client) {
            this.clientToReturn = client;
        }

        public void setShouldReturnNull(boolean value) {
            this.shouldReturnNull = value;
        }

        @Override
        public Client findClientByNric(String nric) throws FinanceProPlusException {
            findByNricCalled = true;
            receivedNric = nric;
            if (shouldReturnNull) {
                return null;
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
    void execute_validArgs_addsTodoToClientAndPrints() throws FinanceProPlusException {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S1234567A d/Review insurance policy by/31-12-2025");
        command.execute(lookUpTable);

        assertTrue(spyClientList.findByNricCalled, "findClientByNric should be called.");
        assertEquals("S1234567A", spyClientList.receivedNric, "NRIC should be parsed from id/ prefix.");
        assertTrue(spyClient.addTodoCalled, "addTodo should be called on the client.");

        String output = outContent.toString();
        assertTrue(output.contains("Noted. I've added this task:"));
        assertTrue(output.contains("Review insurance policy"));
    }

    @Test
    void execute_missingId_throwsException() {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "d/Review policy by/31-12-2025");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Client ID (id/) is required to add a todo.", e.getMessage());
    }

    @Test
    void execute_clientNotFound_throwsException() {
        spyClientList.setShouldReturnNull(true);
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S0000000Z d/Test by/31-12-2025");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Client with NRIC 'S0000000Z' not found.", e.getMessage());
    }

    @Test
    void execute_missingDescription_throwsException() {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S1234567A by/31-12-2025");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Todo description (d/) is required.", e.getMessage());
    }

    @Test
    void execute_missingDueDate_throwsException() {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S1234567A d/Review policy");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Todo due date (by/) is required.", e.getMessage());
    }

    @Test
    void execute_missingBothDescriptionAndDueDate_throwsException() {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S1234567A");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Todo description (d/) and due date (by/) are required.", e.getMessage());
    }

    @Test
    void execute_emptyDescriptionAndDueDate_throwsException() {
        AddClientTodoCommand command = new AddClientTodoCommand("client",
                "id/S1234567A d/ by/");
        FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                () -> command.execute(lookUpTable));
        assertEquals("Todo description (d/) and due date (by/) are required.", e.getMessage());
    }
}


