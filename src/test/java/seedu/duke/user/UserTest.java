package seedu.duke.user;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserTest {

    @Test
    void constructor_validArguments_success() throws FinanceProPlusException {
        User user = new User("n/John Doe e/john@example.com c/98765432 r/FA-12345");
        assertEquals("User Details:\n"
                + "Name: John Doe\n"
                + "Email: john@example.com\n"
                + "Contact: 98765432\n"
                + "Representative No.: FA-12345", user.toString());
    }

    @Test
    void constructor_missingField_throwsFinanceProPlusException() {
        String args = "n/John Doe e/john@example.com c/98765432"; // missing r/
        assertThrows(FinanceProPlusException.class, () -> new User(args));
    }

    @Test
    void constructor_invalidEmailFormat_throwsFinanceProPlusException() {
        String args = "n/John Doe e/johnexample.com c/98765432 r/FA-12345"; // invalid email
        assertThrows(FinanceProPlusException.class, () -> new User(args));
    }

    @Test
    void constructor_invalidContact_throwsFinanceProPlusException() {
        String args = "n/John Doe e/john@example.com c/invalid r/FA-12345";
        assertThrows(FinanceProPlusException.class, () -> new User(args));
    }

    @Test
    void parseUserDetails_validInput_success() {
        String args = "n/Alice Tan e/alice@example.com c/91234567 r/FA-88888";
        Map<String, String> parsed = User.parseUserDetails(args);
        assertEquals("Alice Tan", parsed.get("n"));
        assertEquals("alice@example.com", parsed.get("e"));
        assertEquals("91234567", parsed.get("c"));
        assertEquals("FA-88888", parsed.get("r"));
    }

    @Test
    void parseUserDetails_emptyString_returnsEmptyMap() {
        Map<String, String> parsed = User.parseUserDetails("");
        assertTrue(parsed.isEmpty());
    }

    @Test
    void toStorageString_validUser_correctFormat() throws FinanceProPlusException {
        User user = new User("n/Jane Lim e/jane@example.com c/98761234 r/FA-999");
        assertEquals("n/Jane Lim e/jane@example.com c/98761234 r/FA-999", user.toStorageString());
    }

    @Test
    void toCSVRow_validUser_correctFields() throws FinanceProPlusException {
        User user = new User("n/Jane Lim e/jane@example.com c/98761234 r/FA-999");
        String[] expected = {"Jane Lim", "jane@example.com", "98761234", "FA-999"};
        assertArrayEquals(expected, user.toCSVRow());
    }

    @Test
    void constructor_nullInput_throwsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class, () -> new User(null));
        assertEquals("Arguments for user creation cannot be null or empty", error.getMessage());
    }

    @Test
    void constructor_emptyInput_throwsAssertionError() {
        AssertionError error = assertThrows(AssertionError.class, () -> new User(""));
        assertEquals("Arguments for user creation cannot be null or empty", error.getMessage());
    }
}
