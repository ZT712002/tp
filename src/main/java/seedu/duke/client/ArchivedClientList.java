package seedu.duke.client;

import seedu.duke.container.ListContainer;
import seedu.duke.exception.FinanceProPlusException;
import java.util.ArrayList;

public class ArchivedClientList implements ListContainer {
    private ArrayList<Client> archivedClients;

    public ArchivedClientList() {
        this.archivedClients = new ArrayList<>();
    }

    public void archiveClient(Client client) {
        assert client != null : "Client to archive cannot be null";
        archivedClients.add(client);
        System.out.println("Noted. I've archived this client:");
        System.out.println(client);
    }

    public Client restoreClient(int index) throws FinanceProPlusException {
        if (index < 0 || index >= archivedClients.size()) {
            throw new FinanceProPlusException("Invalid index. Please provide a valid archived client index.");
        }
        return archivedClients.remove(index);
    }

    @Override
    public void listItems() {
        if (archivedClients.isEmpty()) {
            System.out.println("No archived clients found.");
        } else {
            System.out.println("Here are the archived clients:");
            for (int i = 0; i < archivedClients.size(); i++) {
                System.out.println((i + 1) + ". " + archivedClients.get(i).toString());
            }
        }
    }

    @Override
    public void addItem(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("Cannot add items directly to archived list");
    }

    @Override
    public void addItem(String arguments, ListContainer policyList) throws FinanceProPlusException {
        throw new FinanceProPlusException("Cannot add items directly to archived list");
    }

    @Override
    public void deleteItem(String arguments) throws FinanceProPlusException {
        throw new FinanceProPlusException("Use restore command instead");
    }

    @Override
    public int checkDeleteIndex(String arguments) throws FinanceProPlusException {
        int index;
        try {
            index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= archivedClients.size()) {
                throw new FinanceProPlusException("Invalid index. Please provide a valid archived client index.");
            }
        } catch (NumberFormatException e) {
            throw new FinanceProPlusException("Invalid input. Please provide a valid archived client index.");
        }
        return index;
    }

    public ArrayList<Client> getArchivedClients() {
        return archivedClients;
    }
}
