package seedu.duke.task;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @Test
    void constructor_validInput_createsTask() throws FinanceProPlusException {
        Task task = new Task("d/Review client portfolio by/15-03-2024");
        assertEquals("Review client portfolio", task.getDescription());
        assertEquals("15-03-2024", task.getDueDate());
    }

    @Test
    void constructor_missingDescription_throwsException() {
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            new Task("by/15-03-2024");
        });
        assertEquals("Invalid task details. Please provide all required fields: d/DESCRIPTION by/DUE_DATE", 
                exception.getMessage());
    }

    @Test
    void constructor_missingDueDate_throwsException() {
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            new Task("d/Review client portfolio");
        });
        assertEquals("Invalid task details. Please provide all required fields: d/DESCRIPTION by/DUE_DATE", 
                exception.getMessage());
    }

    @Test
    void constructor_invalidDateFormat_throwsException() {
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            new Task("d/Review client portfolio by/2024-03-15");
        });
        assertEquals("Invalid date format. Please use dd-MM-yyyy (e.g., 15-01-2024)", 
                exception.getMessage());
    }

    @Test
    void toString_returnsCorrectFormat() throws FinanceProPlusException {
        Task task = new Task("d/Review client portfolio by/15-03-2024");
        String expected = "Review client portfolio (by: 15-03-2024)";
        assertEquals(expected, task.toString());
    }
}

