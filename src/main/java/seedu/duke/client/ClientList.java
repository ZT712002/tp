package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;
import seedu.duke.policy.ClientPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;

public class ClientList implements ListContainer {
    private static final Logger logger = Logger.getLogger(ClientList.class.getName());
    private ArrayList<Client> clients;

    public ClientList() {
        this.clients = new ArrayList<Client>();
        assert clients != null : "Client list should be initialized properly";
    }

    private static String safeGetFirst(Map<String, List<String>> map, String key) {
        if (map == null) {
            return "";
        }
        List<String> values = map.get(key);
        if (values == null || values.isEmpty()) {
            return "";
        }
        return values.get(0);
    }

    public void addClient(Client client) {
        assert client != null : "Client to be added should not be null";
        int oldSize = clients.size();
        clients.add(client);
        assert clients.size() == oldSize + 1 : "Client list size should increase by 1 after adding a client";
        System.out.println("Noted. I've added this client:");
        System.out.println(client);
    }

    public ArrayList<Client> getClientList() {
        return clients;
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("This method is not implemented for client list");
    }

    @Override
    public void addItem(String arguments, ListContainer policyList) throws FinanceProPlusException {
        Map<String, List<String>> detailsMap = Client.parseClientDetails(arguments);
        String nric = safeGetFirst(detailsMap, "id");
        if (nric.isEmpty()) {
            throw new FinanceProPlusException("NRIC (id/) must be provided.");
        }
        if (findClientByNric(nric) != null) {
            throw new FinanceProPlusException("A client with NRIC '" + nric + "' already exists.");
        }
        Client client = new Client(arguments, policyList);
        addClient(client);
        logger.info("Successfully added new client: " + client.getName());
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if (clients.isEmpty()) {
            System.out.println("No clients to delete.");
            return;
        }
        int oldSize = clients.size();
        int index = checkDeleteIndex(arguments);
        Client removedClient = clients.remove(index);
        assert clients.size() == oldSize - 1 : "Client list size should decrease by 1 after deleting a client";
        System.out.println("Noted. I've removed this client:");
        System.out.println(removedClient.toString());
        logger.info("Successfully deleted client: " + removedClient.getName());
    }

    @Override
    public void listItems() {
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            System.out.println("Here are the clients in your list:");
            for (int i = 0; i < clients.size(); i++) {
                System.out.println((i + 1) + ". " + clients.get(i).toString());
            }
        }
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        int index;
        try {
            index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= clients.size()) {
                throw new FinanceProPlusException("Invalid index. Please provide a valid client index to delete.");
            }
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid input. Please provide a valid client index to delete.");
        }
        logger.fine("Validated delete index: " + index);
        return index;
    }

    public Client findClientByNric(String nric) throws FinanceProPlusException {
        if (nric == null || nric.isEmpty()) {
            throw new FinanceProPlusException("Error: NRIC to find cannot be null or empty. make sure id/ isn't empty");
        }
        for (Client client : this.clients) {
            if (client.getNric().equals(nric)) {
                return client;
            }
        }
        return null;
    }

    public void searchClient(String nric) throws FinanceProPlusException {
        Client foundClient = findClientByNric(nric);
        if (foundClient != null) {
            System.out.println("Client found:");
            System.out.println(foundClient.toString());
        } else {
            System.out.println("No client found with NRIC: " + nric);
        }
    }

    /**
     * Adds a new policy contract to an existing client.
     * This method oversees the validation and creation process.
     *
     * @param arguments      The raw string arguments from the user.
     * @param mainPolicyList The main list of all company policies for validation.
     * @throws FinanceProPlusException If any validation fails.
     */
    public void addPolicyToClient(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
        Map<String, List<String>> argsMap = parseAndValidateAddPolicyArgs(arguments);
        String nric = argsMap.get("id").get(0);
        Client client = findClientByNric(nric);
        if (client == null) {
            throw new FinanceProPlusException("Client with NRIC '" + nric + "' does not exist.");
        }
        String basePolicyName = argsMap.get("p").get(0);
        Policy basePolicy = validateAndGetBasePolicy(client, mainPolicyList, basePolicyName);
        ClientPolicy newClientPolicy = createClientPolicyFromArgs(argsMap, basePolicy);
        client.addPolicy(newClientPolicy);
        System.out.println("Successfully added new policy contract to client " + nric + ".");
        System.out.println("Updated Client Details: " + client);
    }

    private ClientPolicy createClientPolicyFromArgs(Map<String, List<String>> argsMap, Policy basePolicy)
            throws FinanceProPlusException {
        try {
            LocalDate startDate = LocalDate.parse(argsMap.get("s").get(0)); // Assumes YYYY-MM-DD
            LocalDate expiryDate = LocalDate.parse(argsMap.get("e").get(0));
            BigDecimal premium = new BigDecimal(argsMap.get("m").get(0));
            return new ClientPolicy(basePolicy, startDate, expiryDate, premium);
        } catch (DateTimeParseException e) {
            throw new FinanceProPlusException("Invalid date format. Please use YYYY-MM-DD.");
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid premium format. Please enter a valid number (e.g., 150.75).");
        }
    }

    private Policy validateAndGetBasePolicy(Client client, ListContainer mainPolicyList, String basePolicyName)
            throws FinanceProPlusException {
        PolicyList companyPolicies = (PolicyList) mainPolicyList;
        Policy basePolicy = companyPolicies.findPolicyByName(basePolicyName);
        if (basePolicy == null) {
            throw new FinanceProPlusException("Error: Base policy '" + basePolicyName
                    + "' not found in the main list.");
        }
        if (client.hasPolicy(basePolicyName)) {
            throw new FinanceProPlusException("Error: Client " + client.getNric()
                    + " already has a contract for policy '" + basePolicyName + "'.");
        }
        return basePolicy;
    }

    private Map<String, List<String>> parseAndValidateAddPolicyArgs(String arguments) throws FinanceProPlusException {
        Map<String, List<String>> argsMap = Client.parseClientDetails(arguments);
        List<String> requiredKeys = List.of("id", "p", "s", "e", "m");
        for (String key : requiredKeys) {
            if (!argsMap.containsKey(key) || argsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid command. Required fields are missing. "
                        + "Please provide: id/, p/, s/, e/, m/");
            }
        }
        return argsMap;
    }

    public void updatePolicyForClient(String arguments) throws FinanceProPlusException {
        Map<String, List<String>> argsMap = parseAndValidateUpdatePolicyArgs(arguments);
        ClientPolicy clientPolicyToUpdate = findClientPolicyToUpdate(argsMap);
        boolean wasUpdated = applyPolicyUpdatesFromArgs(clientPolicyToUpdate, argsMap);
        if (wasUpdated) {
            System.out.println("Successfully updated policy '" + clientPolicyToUpdate.getName()
                    + "' for client " + argsMap.get("id").get(0) + ".");
            System.out.println("New Details: " + clientPolicyToUpdate);
        } else {
            System.out.println("No updates were applied.");
        }
    }

    private Map<String, List<String>> parseAndValidateUpdatePolicyArgs(String arguments)
            throws FinanceProPlusException {
        Map<String, List<String>> argsMap = Client.parseClientDetails(arguments);
        if (!argsMap.containsKey("id") || !argsMap.containsKey("p")) {
            throw new FinanceProPlusException("Invalid command. Both id/ and p/ are required to identify the policy.");
        }
        if (!argsMap.containsKey("s") && !argsMap.containsKey("e") && !argsMap.containsKey("m")) {
            throw new FinanceProPlusException("Invalid command. You must provide at least one field to update: s/, " +
                    "e/, or m/.");
        }
        return argsMap;
    }

    private ClientPolicy findClientPolicyToUpdate(Map<String, List<String>> argsMap) throws FinanceProPlusException {
        String nric = argsMap.get("id").get(0);
        String basePolicyName = argsMap.get("p").get(0);
        Client client = findClientByNric(nric);
        if (client == null) {
            throw new FinanceProPlusException("Error: Client with NRIC '" + nric + "' not found.");
        }
        Policy clientPolicy = client.getClientPolicyList().findPolicyByName(basePolicyName);

        if (clientPolicy == null) {
            throw new FinanceProPlusException("Error: Client " + nric + " does not have a contract for policy '"
                    + basePolicyName + "'.");
        }
        if (!(clientPolicy instanceof ClientPolicy)) {
            throw new FinanceProPlusException("Internal Error: Policy found is not a ClientPolicy and cannot be " +
                    "updated.");
        }
        return (ClientPolicy) clientPolicy;
    }

    private boolean applyPolicyUpdatesFromArgs(ClientPolicy clientPolicy, Map<String, List<String>> argsMap)
            throws FinanceProPlusException {
        boolean isUpdated = false;
        try {
            if (argsMap.containsKey("s")) {
                clientPolicy.setStartDate(LocalDate.parse(argsMap.get("s").get(0)));
                isUpdated = true;
            }
            if (argsMap.containsKey("e")) {
                clientPolicy.setExpiryDate(LocalDate.parse(argsMap.get("e").get(0)));
                isUpdated = true;
            }
            if (argsMap.containsKey("m")) {
                clientPolicy.setMonthlyPremium(new BigDecimal(argsMap.get("m").get(0)));
                isUpdated = true;
            }
        } catch (DateTimeParseException e) {
            throw new FinanceProPlusException("Invalid date format. Please use YYYY-MM-DD.");
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid premium format. Please enter a valid number.");
        }
        return isUpdated;
    }

    public Client getClientByID(String args) throws FinanceProPlusException {
        Map<String, List<String>> argsMap = Client.parseClientDetails(args);
        String nric = safeGetFirst(argsMap, "id");
        Client client = findClientByNric(nric);
        if (client == null) {
            throw new FinanceProPlusException("Error: Client with NRIC '" + nric + "' not found.");
        }
        assert client != null : "Client should not be null in getClientById";
        return client;
    }

    public List<String> toStorageFormat() {
        List<String> lines = new ArrayList<>();
        for (Client c : clients) {
            lines.add(c.toStorageString());
        }
        return lines;
    }

    public void loadFromStorage(List<String> lines, ListContainer mainPolicyList)
            throws FinanceProPlusException {
        for (String line : lines) {
            clients.add(new Client(line, mainPolicyList));
        }
    }

    public List<String[]> toCSVFormat() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Name", "Contact", "NRIC", "Policy"});
        for (Client c : clients) {
            rows.add(c.toCSVRow());
        }
        return rows;
    }
    /**
     * Deletes a policy from a specific client's policy list by index.
     * The command arguments must contain the client's NRIC (id/) and the policy index (i/).
     *
     * @param arguments The raw string arguments from the user.
     * @throws FinanceProPlusException If arguments are invalid, client is not found, or index is invalid.
     */
    public void deletePolicyForClient(String arguments) throws FinanceProPlusException {
        Map<String, List<String>> argsMap = Client.parseClientDetails(arguments);
        String nric = safeGetFirst(argsMap, "id");
        String indexString = safeGetFirst(argsMap, "i");

        if (nric.isEmpty() || indexString.isEmpty()) {
            throw new FinanceProPlusException("Invalid command. Both client NRIC (id/) and policy index (i/) are " +
                    "required.");
        }
        Client client = findClientByNric(nric);
        if (client == null) {
            throw new FinanceProPlusException("Error: Client with NRIC '" + nric + "' not found.");
        }
        PolicyList clientPolicies = client.getClientPolicyList();
        clientPolicies.deleteItem(indexString);

    }
}



