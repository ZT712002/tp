package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

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
}
