package seedu.duke.task;

import seedu.duke.exception.FinanceProPlusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    private static final String TASK_REGEX = "\\s+(?=[a-z]+/)";
    private String description;
    private String dueDate;

    public Task(String arguments) throws FinanceProPlusException {
        assert arguments != null && !arguments.trim().isEmpty() : "Arguments for task creation cannot be null";
        Map<String, String> detailsMap = parseTaskDetails(arguments);
        List<String> requiredKeys = List.of("d", "by");
        for (String key : requiredKeys) {
            if (!detailsMap.containsKey(key) || detailsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid task details. Please provide all required fields: "
                        + "d/DESCRIPTION by/DUE_DATE");
            }
        }
        description = detailsMap.get("d");
        dueDate = detailsMap.get("by");
        assert this.description != null && !description.isEmpty() : "Description should not be null or empty";
        assert this.dueDate != null && !dueDate.isEmpty() : "Due date should not be null or empty";

        validateDateFormat(dueDate);
    }

    private void validateDateFormat(String dateString) throws FinanceProPlusException {
        if (!dateString.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new FinanceProPlusException("Invalid date format. Please use dd-MM-yyyy (e.g., 15-01-2024)");
        }
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public static Map<String, String> parseTaskDetails(String taskDetails) {
        assert taskDetails != null : "Input string for parsing cannot be null";
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = taskDetails.trim();
        String[] parts = trimmedDetails.split(TASK_REGEX);
        for (String part : parts) {
            String[] keyValue = part.split("/", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].trim();
                detailsMap.put(key, value);
            }
        }
        assert detailsMap != null : "The resulting details map should not be null";
        return detailsMap;
    }

    @Override
    public String toString() {
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert dueDate != null && !dueDate.isEmpty() : "Due date should not be null or empty";
        return description + " (by: " + dueDate + ")";
    }

    public String toStorageString() {
        return String.format("d/%s by/%s", description, dueDate);
    }

    public String[] toCSVRow() {
        return new String[]{description, dueDate, "Pending"};
    }

}

