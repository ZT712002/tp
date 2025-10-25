package seedu.duke.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.ClientPolicy;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientListTest {

    private ClientList clientList;
    private ListContainer mainPolicyList;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws FinanceProPlusException {
        clientList = new ClientList();


        mainPolicyList = new PolicyList();
        Policy healthPolicy = new Policy("n/1234 d/Health Test", true);
        Policy lifePolicy = new Policy("n/1233 d/LifeTest", true);
        ((PolicyList) mainPolicyList).addPolicy(healthPolicy);
        ((PolicyList) mainPolicyList).addPolicy(lifePolicy);


        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Nested
    class AddItemTests {
        @Test
        void addItem_validNewClient_addsSuccessfully() throws FinanceProPlusException {
            String args = "n/John Doe c/12345678 id/S1234567A";
            assertEquals(0, clientList.getClientList().size());

            clientList.addItem(args, mainPolicyList);

            assertEquals(1, clientList.getClientList().size());
            assertNotNull(clientList.findClientByNric("S1234567A"));
            assertTrue(outContent.toString().contains("Noted. I've added this client:"));
            assertTrue(outContent.toString().contains("Name: John Doe"));
        }

        @Test
        void addItem_duplicateNric_throwsException() throws FinanceProPlusException {
            String args = "n/John Doe c/12345678 id/S1234567A";
            clientList.addItem(args, mainPolicyList);

            String argsDuplicate = "n/Jane Doe c/87654321 id/S1234567A";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addItem(argsDuplicate, mainPolicyList));

            assertEquals("A client with NRIC 'S1234567A' already exists.", e.getMessage());
            assertEquals(1, clientList.getClientList().size());
        }

        @Test
        void addItem_missingNric_throwsException() {
            String args = "n/John Doe c/12345678";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addItem(args, mainPolicyList));
            assertEquals("NRIC (id/) must be provided.", e.getMessage());
        }

        @Test
        void addItem_unimplementedMethod_throwsException() {
            assertThrows(FinanceProPlusException.class, () -> clientList.addItem("some args"));
        }
    }

    @Nested
    class DeleteItemTests {
        @BeforeEach
        void addClientForDeletion() throws FinanceProPlusException {
            clientList.addItem("n/Client One c/111 id/T111A", mainPolicyList);
            clientList.addItem("n/Client Two c/222 id/T222B", mainPolicyList);
        }

        @Test
        void deleteItem_validIndex_removesSuccessfully() throws FinanceProPlusException {
            assertEquals(2, clientList.getClientList().size());
            clientList.deleteItem("1"); // Delete "Client One"
            assertEquals(1, clientList.getClientList().size());
            assertNull(clientList.findClientByNric("T111A")); // Verify it's gone
            assertNotNull(clientList.findClientByNric("T222B")); // Verify other remains
            assertTrue(outContent.toString().contains("Noted. I've removed this client:"));
            assertTrue(outContent.toString().contains("Name: Client One"));
        }

        @Test
        void deleteItem_invalidIndex_throwsException() {
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.deleteItem("3"));
            assertEquals("Invalid index. Please provide a valid client index to delete.", e.getMessage());
        }

        @Test
        void deleteItem_nonNumericIndex_throwsException() {
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.deleteItem("abc"));
            assertEquals("Invalid input. Please provide a valid client index to delete.", e.getMessage());
        }

        @Test
        void deleteItem_emptyList_printsMessage() throws FinanceProPlusException {
            ClientList emptyList = new ClientList();
            emptyList.deleteItem("1");
            assertTrue(outContent.toString().contains("No clients to delete."));
        }
    }

    @Nested
    class ListItemsTests {
        @Test
        void listItems_nonEmptyList_printsList() throws FinanceProPlusException {
            clientList.addItem("n/Client One c/111 id/T111A", mainPolicyList);
            clientList.addItem("n/Client Two c/222 id/T222B", mainPolicyList);
            clientList.listItems();
            String output = outContent.toString();
            assertTrue(output.contains("Here are the clients in your list:"));
            assertTrue(output.contains("1. Name: Client One"));
            assertTrue(output.contains("2. Name: Client Two"));
        }

        @Test
        void listItems_emptyList_printsMessage() {
            clientList.listItems();
            assertTrue(outContent.toString().contains("No clients found."));
        }
    }

    @Nested
    class AddPolicyToClientTests {
        @BeforeEach
        void addClientForPolicy() throws FinanceProPlusException {
            clientList.addItem("n/Client One c/111 id/T111A", mainPolicyList);
        }

        @Test
        void addPolicyToClient_validArgs_addsSuccessfully() throws FinanceProPlusException {
            String args = "id/T111A p/1234 s/2023-01-01 e/2024-01-01 m/150.50";
            Client client = clientList.findClientByNric("T111A");
            assertEquals(0, client.getClientPolicyList().getPolicyList().size());

            clientList.addPolicyToClient(args, mainPolicyList);

            assertEquals(1, client.getClientPolicyList().getPolicyList().size());
            assertTrue(client.hasPolicy("1234"));
            assertTrue(outContent.toString().contains("Successfully added new policy contract"));
        }

        @Test
        void addPolicyToClient_clientNotFound_throwsException() {
            String args = "id/EMPTY p/1234 s/2023-01-01 e/2024-01-01 m/150.50";
            assertThrows(FinanceProPlusException.class, () -> clientList.addPolicyToClient(args, mainPolicyList));
        }

        @Test
        void addPolicyToClient_basePolicyNotFound_throwsException() {
            String args = "id/T111A p/12345 s/2023-01-01 e/2024-01-01 m/150.50";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addPolicyToClient(args, mainPolicyList));
            assertTrue(e.getMessage().contains("Base policy '12345' not found"));
        }

        @Test
        void addPolicyToClient_clientAlreadyHasPolicy_throwsException() throws FinanceProPlusException {
            String args = "id/T111A p/1234 s/2023-01-01 e/2024-01-01 m/150.50";
            clientList.addPolicyToClient(args, mainPolicyList); // Add once

            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addPolicyToClient(args, mainPolicyList)); // Try to add again
            assertTrue(e.getMessage().contains("already has a contract for policy"));
        }

        @Test
        void addPolicyToClient_invalidDateFormat_throwsException() {
            String args = "id/T111A p/1234 s/2023/01/01 e/2024-01-01 m/150.50";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addPolicyToClient(args, mainPolicyList));
            assertEquals("Invalid date format. Please use YYYY-MM-DD.", e.getMessage());
        }

        @Test
        void addPolicyToClient_invalidPremiumFormat_throwsException() {
            String args = "id/T111A p/1234 s/2023-01-01 e/2024-01-01 m/abc";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addPolicyToClient(args, mainPolicyList));
            assertEquals("Invalid premium format. Please enter a valid number (e.g., 150.75).", e.getMessage());
        }

        @Test
        void addPolicyToClient_missingArgument_throwsException() {
            String args = "id/T111A p/1234 s/2023-01-01 e/2024-01-01"; // Missing m/
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.addPolicyToClient(args, mainPolicyList));
            assertTrue(e.getMessage().contains("Required fields are missing."));
        }
    }

    @Nested
    class UpdatePolicyForClientTests {
        @BeforeEach
        void setupClientWithPolicy() throws FinanceProPlusException {
            clientList.addItem("n/Client One c/111 id/T111A", mainPolicyList);
            String addArgs = "id/T111A p/1233 s/2023-01-01 e/2024-01-01 m/150.00";
            clientList.addPolicyToClient(addArgs, mainPolicyList);
        }

        @Test
        void updatePolicyForClient_validArgs_updatesSuccessfully() throws FinanceProPlusException {
            String updateArgs = "id/T111A p/1233 e/2025-12-31 m/200.00";
            clientList.updatePolicyForClient(updateArgs);

            Client client = clientList.findClientByNric("T111A");
            Policy policy = client.getClientPolicyList().findPolicyByName("1233");

            assertEquals(LocalDate.parse("2025-12-31"), ((ClientPolicy) policy).getExpiryDate());
            assertEquals(0, ((ClientPolicy) policy).getMonthlyPremium().compareTo(new
                    java.math.BigDecimal("200.00")));
            assertTrue(outContent.toString().contains("Successfully updated policy"));
        }

        @Test
        void updatePolicyForClient_clientNotFound_throwsException() {
            String updateArgs = "id/NONEXISTENT p/1234 e/2025-12-31";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.updatePolicyForClient(updateArgs));
            assertTrue(e.getMessage().contains("Client with NRIC 'NONEXISTENT' not found."));
        }

        @Test
        void updatePolicyForClient_clientDoesNotHavePolicy_throwsException() {
            String updateArgs = "id/T111A p/1234 e/2025-12-31";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.updatePolicyForClient(updateArgs));
            assertTrue(e.getMessage().contains("does not have a contract for policy '1234'."));
        }

        @Test
        void updatePolicyForClient_missingId_throwsException() {
            String updateArgs = "p/1234 e/2025-12-31";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.updatePolicyForClient(updateArgs));
            assertEquals("Invalid command. Both id/ and p/ are required to identify the policy.",
                    e.getMessage());
        }

        @Test
        void updatePolicyForClient_noUpdateFields_throwsException() {
            String updateArgs = "id/T111A p/1234"; // No s/, e/, or m/
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.updatePolicyForClient(updateArgs));
            assertTrue(e.getMessage().contains("You must provide at least one field to update"));
        }

        @Test
        void updatePolicyForClient_noChangesApplied_printsMessage() throws FinanceProPlusException {
            String updateArgs = "id/T111A p/1233 e/2025-12-31";
            clientList.updatePolicyForClient(updateArgs);
            assertTrue(outContent.toString().contains("Successfully updated policy"));
        }

    }
    @Nested
    class GetClientByIDTests {
        private final String realNric = "G1234567X";
        private final String fakeNric = "G7654321Z";

        @BeforeEach
        void setupClient() throws FinanceProPlusException {
            clientList.addItem("n/James Bond c/007 id/" + realNric, mainPolicyList);
            outContent.reset();
        }

        @Test
        void getClientByID_existingClient_returnsCorrectClient() throws FinanceProPlusException {
            String args = "id/" + realNric;
            Client foundClient = clientList.getClientByID(args);
            assertNotNull(foundClient);
            assertEquals("James Bond", foundClient.getName());
            assertEquals(realNric, foundClient.getNric());
        }

        @Test
        void getClientByID_nonExistentClient_throwsSpecificException() {
            String args = "id/" + fakeNric;
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.getClientByID(args));
            assertEquals("Error: Client with NRIC '" + fakeNric + "' not found.", e.getMessage());
        }

        @Test
        void getClientByID_missingIdPrefix_throwsExceptionFromUnderlyingMethod() {
            String args = "n/Some Name";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.getClientByID(args));
            assertEquals("Error: NRIC to find cannot be null or empty. make sure id/ isn't empty"
                    , e.getMessage());
        }

        @Test
        void getClientByID_emptyIdValue_throwsExceptionFromUnderlyingMethod() {

            String args = "id/";
            FinanceProPlusException e = assertThrows(FinanceProPlusException.class,
                    () -> clientList.getClientByID(args));
            assertEquals("Error: NRIC to find cannot be null or empty. make sure id/ isn't empty", e.getMessage());
        }

        @Test
        void getClientByID_argsWithExtraData_returnsCorrectClient() throws FinanceProPlusException {
            String args = "n/Irrelevant c/999 id/" + realNric + " p/SomePolicy";
            Client foundClient = clientList.getClientByID(args);
            assertNotNull(foundClient);
            assertEquals(realNric, foundClient.getNric());
        }

        @Test
        void getClientByID_argsWithWhitespace_returnsCorrectClient() throws FinanceProPlusException {
            String args = "  id/ " + realNric + "  ";
            Client foundClient = clientList.getClientByID(args);
            assertNotNull(foundClient);
            assertEquals(realNric, foundClient.getNric());
        }
    }
}
