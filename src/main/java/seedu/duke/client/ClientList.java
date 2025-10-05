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
    public void deleteItem(String arguments) {

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
}
