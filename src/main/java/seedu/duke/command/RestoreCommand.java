package seedu.duke.command;

import seedu.duke.client.ArchivedClientList;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class RestoreCommand extends Command {
    private String arguments;

    public RestoreCommand(String subtype, String arguments) {
        assert subtype != null && subtype.equals("client") : "RestoreCommand only supports client subtype";
        this.subtype = subtype;
        this.arguments = arguments;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        assert lookUpTable != null : "LookUpTable cannot be null";

        ArchivedClientList archivedList = (ArchivedClientList) lookUpTable.getList("archived");
        ClientList clientList = (ClientList) lookUpTable.getList("client");

        if (archivedList.getArchivedClients().isEmpty()) {
            System.out.println("No archived clients to restore.");
            return;
        }

        int index = archivedList.checkDeleteIndex(arguments);
        Client clientToRestore = archivedList.restoreClient(index);
        clientList.addClient(clientToRestore);
        System.out.println("Successfully restored client from archive.");
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
