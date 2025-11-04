package seedu.duke.command;

import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

import java.util.HashMap;
import java.util.Map;

public class AddClientTodoCommand extends Command {
    private String arguments;

    public AddClientTodoCommand(String subtype, String args) {
        this.subtype = subtype;
        this.arguments = args;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        ClientList clientList = (ClientList) lookUpTable.getList(subtype);
        assert clientList != null : "Client list should not be null";

        // Parse arguments to extract client ID and todo details
        Map<String, String> argsMap = parseArguments(arguments);

        // Get the client by NRIC
        String nric = argsMap.get("id");
        if (nric == null || nric.isEmpty()) {
            throw new FinanceProPlusException("Client ID (id/) is required to add a todo.");
        }

        Client client = clientList.findClientByNric(nric);
        if (client == null) {
            throw new FinanceProPlusException("Client with NRIC '" + nric + "' not found.");
        }

        // Extract todo details (d/ and by/)
        String todoDetails = extractTodoDetails(argsMap);
        client.addTodo(todoDetails);
    }

    private Map<String, String> parseArguments(String args) {
        Map<String, String> detailsMap = new HashMap<>();
        String trimmedDetails = args.trim();
        String[] parts = trimmedDetails.split("\\s+(?=[a-z]+/)");
        for (String part : parts) {
            String[] keyValue = part.split("/", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].trim();
                detailsMap.put(key, value);
            }
        }
        return detailsMap;
    }

    private String extractTodoDetails(Map<String, String> argsMap) throws FinanceProPlusException {
        String description = argsMap.get("d");
        String dueDate = argsMap.get("by");

        boolean missingDescription = (description == null || description.isEmpty());
        boolean missingDueDate = (dueDate == null || dueDate.isEmpty());

        if (missingDescription && missingDueDate) {
            throw new FinanceProPlusException("Todo description (d/) and due date (by/) are required.");
        }
        if (missingDescription) {
            throw new FinanceProPlusException("Todo description (d/) is required.");
        }
        if (missingDueDate) {
            throw new FinanceProPlusException("Todo due date (by/) is required.");
        }

        return "d/" + description + " by/" + dueDate;
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}


