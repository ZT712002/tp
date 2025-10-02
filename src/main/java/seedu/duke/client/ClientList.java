package seedu.duke.client;

import java.util.ArrayList;

public class ClientList {
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
