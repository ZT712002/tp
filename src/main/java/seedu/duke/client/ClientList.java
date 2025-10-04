package seedu.duke.client;

import seedu.duke.container.ListContainer;

import java.util.ArrayList;

public class ClientList implements ListContainer {
    private ArrayList<Client> clients;
    public ClientList() {
        this.clients = new ArrayList<Client>();
    }


    public void addClient(Client client) {
        clients.add(client);
    }
    public ArrayList<Client> getClientList() {
        return clients;
    }
}
