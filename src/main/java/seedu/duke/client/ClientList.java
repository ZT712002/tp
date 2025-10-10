package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;

import java.util.ArrayList;

public class ClientList implements ListContainer {
    private ArrayList<Client> clients;
    public ClientList() {
        this.clients = new ArrayList<Client>();
    }


    public void addClient(Client client) {
        clients.add(client);
        System.out.println("Noted. I've added this client:");
        System.out.println(client.toString());
    }
    public ArrayList<Client> getClientList() {
        return clients;
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        Client client = new Client(arguments);
        addClient(client);
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        if(clients.size() == 0) {
            System.out.println("No clients to delete.");
            return;
        }
        int index = checkDeleteIndex(arguments);
        Client removedClient = clients.remove(index);
        System.out.println("Noted. I've removed this client:");
        System.out.println(removedClient.toString());
    }

    @Override
    public void listItems() {
        if (clients.size() == 0) {
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
        return index;
    }
}
