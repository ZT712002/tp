package seedu.duke.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class UserListTest {
    private UserList userList;

    @BeforeEach
    void setUp() {
        userList = new UserList();
    }

    @Test
    void addItem_validUser_successfullyAddsUser() throws FinanceProPlusException {
        userList.addItem("n/John Doe e/john@example.com c/98765432 r/FA-12345");
        assertTrue(userList.hasUser());
        List<String> storage = userList.toStorageFormat();
        assertEquals(1, storage.size());
        assertTrue(storage.get(0).contains("n/John Doe"));
    }

    @Test
    void addItem_whenUserAlreadyExists_throwsFinanceProPlusException() throws FinanceProPlusException {
        userList.addItem("n/John Doe e/john@example.com c/98765432 r/FA-12345");
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class,
                () -> userList.addItem("n/Jane Doe e/jane@example.com c/91234567 r/FA-54321"));
        assertEquals("A user already exists. Edit user to update information.", exception.getMessage());
    }

    @Test
    void deleteItem_always_throwsFinanceProPlusException() {
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class,
                () -> userList.deleteItem(""));
        assertEquals("Not implemented for UserList class", exception.getMessage());
    }

    @Test
    void loadFromStorage_validUserLine_restoresUser() throws FinanceProPlusException {
        userList.loadFromStorage(List.of("n/John Doe e/john@example.com c/98765432 r/FA-12345"));
        assertTrue(userList.hasUser());
        List<String> lines = userList.toStorageFormat();
        assertEquals(1, lines.size());
        assertEquals("n/John Doe e/john@example.com c/98765432 r/FA-12345", lines.get(0));
    }

    @Test
    void toStorageFormat_noUser_returnsEmptyList() {
        List<String> lines = userList.toStorageFormat();
        assertTrue(lines.isEmpty());
    }

    @Test
    void editUser_validUpdate_replacesOldUser() throws FinanceProPlusException {
        userList.addItem("n/John Doe e/john@example.com c/98765432 r/FA-12345");
        userList.editUser("n/Jane Doe e/jane@example.com c/91234567 r/FA-54321");
        List<String> lines = userList.toStorageFormat();
        assertTrue(lines.get(0).contains("Jane Doe"));
        assertFalse(lines.get(0).contains("John Doe"));
    }

    @Test
    void editUser_nullArguments_throwsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class, () -> userList.editUser(null));
        assertEquals("Arguments for edit cannot be null or empty", error.getMessage());
    }

    @Test
    void checkDeleteIndex_noUser_throwsFinanceProPlusException() {
        FinanceProPlusException exception = assertThrows(FinanceProPlusException.class,
                () -> userList.checkDeleteIndex(""));
        assertEquals("No user to delete.", exception.getMessage());
    }

    @Test
    void checkDeleteIndex_userExists_returnsZero() throws FinanceProPlusException {
        userList.addItem("n/John Doe e/john@example.com c/98765432 r/FA-12345");
        int index = userList.checkDeleteIndex("");
        assertEquals(0, index);
    }

    @Test
    void listItems_noUser_printsNoUserFound() throws FinanceProPlusException {
        UserList userList = new UserList();
        // Capture console output
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(output));

        userList.listItems();
        String printed = output.toString().trim();

        assertTrue(printed.contains("No user found."));
    }

    @Test
    void listItems_withUser_printsUserInfo() throws FinanceProPlusException {
        UserList userList = new UserList();
        userList.addItem("n/John Doe e/john@example.com c/98765432 r/FA-12345");

        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(output));

        userList.listItems();
        String printed = output.toString();

        assertTrue(printed.contains("Here is the current user information:"));
        assertTrue(printed.contains("John Doe"));
    }

    @Test
    void addItem_withListContainer_throwsFinanceProPlusException() {
        UserList userList = new UserList();
        FinanceProPlusException ex = assertThrows(FinanceProPlusException.class,
                () -> userList.addItem("n/John e/john@example.com c/98765432 r/FA-12345", null));
        assertEquals("Implemented only on ClientList class", ex.getMessage());
    }

    @Test
    void toCSVFormat_noUser_returnsHeaderOnly() {
        UserList userList = new UserList();
        List<String[]> csv = userList.toCSVFormat();
        assertEquals(1, csv.size());
        assertArrayEquals(new String[]{"Name", "Email", "Contact", "Representative"}, csv.get(0));
    }

    @Test
    void toCSVFormat_withUser_returnsHeaderAndDataRow() throws FinanceProPlusException {
        UserList userList = new UserList();
        userList.addItem("n/Jane Doe e/jane@example.com c/98765432 r/FA-99999");
        List<String[]> csv = userList.toCSVFormat();

        assertEquals(2, csv.size());
        assertEquals("Jane Doe", csv.get(1)[0]);
        assertEquals("jane@example.com", csv.get(1)[1]);
    }

}
