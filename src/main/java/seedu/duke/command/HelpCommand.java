package seedu.duke.command;

import seedu.duke.container.LookUpTable;

public class HelpCommand extends Command {
    public HelpCommand() {

    }
    @Override
    public void execute(LookUpTable lookUpTable) {

    }
    @Override
    public void printExecutionMessage() {
        introduction();
        clientManagement();
        clientToDo();
        listCommands();
        meetingManagement();
        policyManagement();
        clientPolicy();
        tasks();
        userProfile();
        miscs();
    }

    private static void miscs() {
        System.out.println("--- Miscellaneous ---");
        System.out.println("- help: Shows this list of commands.");
        System.out.println("- exit: Exits the application.");
        System.out.println("---------------------------------------------------------------------------------");
    }

    private static void userProfile() {
        System.out.println("--- User Profile Management ---");
        System.out.println("- user add n/<NAME> e/<EMAIL> c/<CONTACT> r/<REP_NUMBER>: Adds your user profile.");
        System.out.println("- user view: Displays your user profile.");
        System.out.println("- user edit: Edits your user profile.\n");
    }

    private static void tasks() {
        System.out.println("--- Standalone Task Management ---");
        System.out.println("- task add d/<DESCRIPTION> by/<dd-MM-yyyy>: Creates a new standalone task.");
        System.out.println("- task delete <INDEX>: Deletes a task by its index.\n");
    }

    private static void policyManagement() {
        System.out.println("--- Base Policy Management ---");
        System.out.println("- policy add n/<NAME> d/<DETAILS>: Creates a new base policy template.");
        System.out.println("- policy delete <INDEX>: Deletes a base policy template.\n");
    }

    private static void meetingManagement() {
        System.out.println("--- Meeting Management ---");
        System.out.println("- meeting add t/<TITLE> c/<CLIENT> d/<dd-MM-yyyy> from/<HH:mm> [to/<HH:mm>]: Schedules a new meeting.");
        System.out.println("- meeting delete <INDEX>: Deletes a meeting by its index.");
        System.out.println("- meeting forecast: Shows meetings scheduled for the next 7 days.\n");
    }

    private static void listCommands() {
        System.out.println("--- General Listing ---");
        System.out.println("- list client: Shows a summary of all active clients.");
        System.out.println("- list archived: Shows a summary of all archived clients.");
        System.out.println("- list policy: Shows all available base policy templates.");
        System.out.println("- list meeting: Shows all scheduled meetings.");
        System.out.println("- list task: Shows all standalone tasks.\n");
    }

    private static void clientToDo() {
        System.out.println("--- Client Task (To-Do) Management ---");
        System.out.println("- client addtodo id/<NRIC> d/<DESC> by/<dd-MM-yyyy>: Adds a to-do for a client.");
        System.out.println("- client listtodos id/<NRIC>: Lists all to-dos for a specific client.\n");
    }

    private static void clientPolicy() {
        System.out.println("--- Client Policy Management ---");
        System.out.println("- client addpolicy id/<NRIC> p/<POLICY> s/<dd-MM-yyyy> e/<dd-MM-yyyy> m/<PREMIUM>: Adds a policy to a client.");
        System.out.println("- client updatepolicy id/<NRIC> p/<POLICY> [s/...] [e/...] [m/...]: Updates a client's policy.");
        System.out.println("- client deletepolicy id/<NRIC> i/<INDEX>: Deletes a policy from a client.\n");
    }

    private static void clientManagement() {
        System.out.println("--- Client Management ---");
        System.out.println("- client add n/<NAME> c/<CONTACT> id/<NRIC>: Creates a new client.");
        System.out.println("- client view id/<NRIC>: Displays full details for a client.");
        System.out.println("- client delete <INDEX>: Deletes a client by their index in the list.");
        System.out.println("- client search <NRIC>: Searches for a client by their NRIC.");
        System.out.println("- client archive <INDEX>: Moves a client to the archive.");
        System.out.println("- client restore <INDEX>: Restores a client from the archive.\n");
    }

    private static void introduction() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("FinancePro+ Command List");
        System.out.println("For more details, please refer to the User Guide.");
        System.out.println("---------------------------------------------------------------------------------\n");
    }
}
