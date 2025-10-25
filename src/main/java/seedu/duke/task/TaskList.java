package seedu.duke.task;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TaskList implements ListContainer {
    private static final Logger logger = Logger.getLogger(TaskList.class.getName());
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "TaskList should not be null after initialization";
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        Task task = new Task(arguments);
        assert task != null : "Task to be added should not be null";
        assert tasks != null : "TaskList should not be null when adding an item";
        int oldSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == oldSize + 1: "TaskList size should increase by 1 after adding an item";
        System.out.println("Noted. I've added this task:");
        System.out.println(task.toString());
        logger.info("Successfully added new task: " + task.getDescription());
    }

    @Override
    public void addItem(String arguments, ListContainer listContainer) throws FinanceProPlusException {
        throw new FinanceProPlusException("Implemented only on ClientList class");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if (tasks.size() == 0) {
            System.out.println("No tasks to delete.");
            return;
        }
        int oldSize = tasks.size();
        int index = checkDeleteIndex(arguments);
        Task removedTask = tasks.remove(index);
        assert tasks.size() == oldSize - 1 : "Task list size should decrease by 1 after deleting a task";
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask.toString());
        logger.info("Successfully deleted task: " + removedTask.getDescription());
    }

    @Override
    public void listItems() throws FinanceProPlusException {
        if (tasks.size() == 0) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        int index;
        try {
            index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new FinanceProPlusException("Invalid index. Please provide a valid task index to delete.");
            }
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid input. Please provide a valid task index to delete.");
        }
        logger.fine("Validated delete index: " + index);
        return index;
    }

    public int getSize() {
        return tasks.size();
    }
}

