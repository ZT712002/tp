package seedu.duke.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.FinanceProPlusException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {

    private TaskList taskList;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void addItem_validTask_addsToList() throws FinanceProPlusException {
        taskList.addItem("d/Review portfolio by/15-03-2024");
        assertEquals(1, taskList.getSize());
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Noted. I've added this task:"));
        assertTrue(output.contains("Review portfolio"));
    }

    @Test
    void deleteItem_validIndex_removesTask() throws FinanceProPlusException {
        taskList.addItem("d/Review portfolio by/15-03-2024");
        outputStreamCaptor.reset();
        taskList.deleteItem("1");
        assertEquals(0, taskList.getSize());
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Noted. I've removed this task:"));
    }

    @Test
    void deleteItem_emptyList_printsNoTasksMessage() throws FinanceProPlusException {
        taskList.deleteItem("1");
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No tasks to delete."));
    }

    @Test
    void deleteItem_invalidIndex_throwsException() throws FinanceProPlusException {
        taskList.addItem("d/Review portfolio by/15-03-2024");
        Exception exception = assertThrows(FinanceProPlusException.class, () -> {
            taskList.deleteItem("5");
        });
        assertEquals("Invalid index. Please provide a valid task index to delete.", exception.getMessage());
    }

    @Test
    void listItems_emptyList_printsNoTasksMessage() throws FinanceProPlusException {
        taskList.listItems();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("No tasks found."));
    }

    @Test
    void listItems_withTasks_printsAllTasks() throws FinanceProPlusException {
        taskList.addItem("d/Review portfolio by/15-03-2024");
        taskList.addItem("d/Call client by/20-03-2024");
        outputStreamCaptor.reset();
        taskList.listItems();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Here are the tasks in your list:"));
        assertTrue(output.contains("1. Review portfolio"));
        assertTrue(output.contains("2. Call client"));
    }
}

