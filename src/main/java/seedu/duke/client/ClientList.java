package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import seedu.duke.policy.Policy;
import seedu.duke.policy.PolicyList;
import seedu.duke.policy.ClientPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.math.BigDecimal;

public class ClientList implements ListContainer {
    private static final Logger logger =Logger.getLogger(ClientList.class.getName());
    private ArrayList<Client> clients;
    public ClientList() {
        this.clients = new ArrayList<Client>();
        assert clients != null : "Client list should be initialized properly";
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
        Map<String, String> detailsMap = Client.parseClientDetails(arguments);
        String nric = detailsMap.get("id");
        if (nric == null || nric.isEmpty()) {
            throw new FinanceProPlusException("NRIC (id/) must be provided.");
        }
        if (findClientByNric(nric).isPresent()) {
            throw new FinanceProPlusException("A client with NRIC '"
                    + nric + "' already exists.");
        }
        Client client = new Client(arguments, policyList);
        addClient(client);
        logger.info("Successfully added new client: " + client.getName());
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if(clients.isEmpty()) {
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
    public Optional<Client> findClientByNric(String nric) {
        assert nric != null && !nric.isEmpty() : "NRIC to find cannot be null or empty";
        return clients.stream()
                .filter(client -> client.getNric().equals(nric))
                .findFirst();
    }

    /**
     * Adds a new policy contract to an existing client.
     * This method oversees the validation and creation process.
     *
     * @param arguments The raw string arguments from the user.
     * @param mainPolicyList The main list of all company policies for validation.
     * @throws FinanceProPlusException If any validation fails.
     */
    public void addPolicyToClient(String arguments, ListContainer mainPolicyList) throws FinanceProPlusException {
        Map<String, String> argsMap = parseAndValidateAddPolicyArgs(arguments);
        String nric = argsMap.get("id");
        Client client = findClientByNric(nric)
                .orElseThrow(() -> new FinanceProPlusException("Error: Client with NRIC '" + nric + "' not found."));
        String basePolicyName = argsMap.get("p");
        Policy basePolicy = validateAndGetBasePolicy(client, mainPolicyList, basePolicyName);
        ClientPolicy newClientPolicy = createClientPolicyFromArgs(argsMap, basePolicy);
        client.addPolicy(newClientPolicy);
        System.out.println("Successfully added new policy contract to client " + nric + ".");
        System.out.println("Updated Client Details: " + client);
    }
    /**
     * Creates a ClientPolicy instance from the parsed arguments map.
     *
     * @param argsMap The map of parsed arguments.
     * @param basePolicy The base Policy this contract is based on.
     * @return A new ClientPolicy object.
     * @throws FinanceProPlusException If date or premium formats are invalid.
     */
    private ClientPolicy createClientPolicyFromArgs(Map<String, String> argsMap, Policy basePolicy)
            throws FinanceProPlusException {
        try {
            LocalDate startDate = LocalDate.parse(argsMap.get("s")); // Assumes YYYY-MM-DD
            LocalDate expiryDate = LocalDate.parse(argsMap.get("e"));
            BigDecimal premium = new BigDecimal(argsMap.get("m"));

            return new ClientPolicy(basePolicy, startDate, expiryDate, premium);
        } catch (DateTimeParseException e) {
            throw new FinanceProPlusException("Invalid date format. Please use YYYY-MM-DD.");
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid premium format. Please enter a valid number (e.g., 150.75).");
        }
    }
    /**
     * Finds the base policy in the main list and ensures the client doesn't already own it.
     *
     * @param client The client to check against.
     * @param mainPolicyList The main list of company policies.
     * @param basePolicyName The name of the policy to validate.
     * @return The validated base Policy object.
     * @throws FinanceProPlusException If the policy doesn't exist or the client already has it.
     */
    private Policy validateAndGetBasePolicy(Client client, ListContainer mainPolicyList, String basePolicyName)
            throws FinanceProPlusException {
        PolicyList companyPolicies = (PolicyList) mainPolicyList;
        Policy basePolicy = companyPolicies.findPolicyByName(basePolicyName)
                .orElseThrow(() -> new FinanceProPlusException("Error: Base policy '" + basePolicyName
                        + "' not found in the main list."));

        if (client.hasPolicy(basePolicyName)) {
            throw new FinanceProPlusException("Error: Client " + client.getNric()
                    + " already has a contract for policy '" + basePolicyName + "'.");
        }
        return basePolicy;
    }
    /**
     * Parses the argument string and validates that all required keys are present.
     *
     * @param arguments The raw command arguments.
     * @return A map of the parsed arguments.
     * @throws FinanceProPlusException If any required key is missing.
     */
    private Map<String, String> parseAndValidateAddPolicyArgs(String arguments) throws FinanceProPlusException {
        Map<String, String> argsMap = Client.parseClientDetails(arguments);
        List<String> requiredKeys = List.of("id", "p", "s", "e", "m");
        for (String key : requiredKeys) {
            if (!argsMap.containsKey(key) || argsMap.get(key).isEmpty()) {
                throw new FinanceProPlusException("Invalid command. Required fields are missing. "
                        + "Please provide: id/, p/, s/, e/, m/");
            }
        }
        return argsMap;
    }

}


