package seedu.duke.command;

import seedu.duke.client.ArchivedClientList;
import seedu.duke.client.Client;
import seedu.duke.client.ClientList;
import seedu.duke.container.LookUpTable;
import seedu.duke.exception.FinanceProPlusException;

public class ArchiveCommand extends Command {
    private String arguments;

    public ArchiveCommand(String subtype, String arguments) {
        assert subtype != null && subtype.equals("client") : "ArchiveCommand only supports client subtype";
        this.subtype = subtype;
        this.arguments = arguments;
    }

    @Override
    public void execute(LookUpTable lookUpTable) throws FinanceProPlusException {
        assert lookUpTable != null : "LookUpTable cannot be null";

        ClientList clientList = (ClientList) lookUpTable.getList("client");
        ArchivedClientList archivedList = (ArchivedClientList) lookUpTable.getList("archived");

        if (clientList.getClientList().isEmpty()) {
            System.out.println("No clients to archive.");
            return;
        }

        int index = clientList.checkDeleteIndex(arguments);
        Client clientToArchive = clientList.getClientList().remove(index);
        archivedList.archiveClient(clientToArchive);
    }

    @Override
    public void printExecutionMessage() {
        System.out.println("----------------------------------------------------");
    }
}
