package seedu.duke.command;

public class DeleteCommand extends Command{

    private String arguments;
    public DeleteCommand(String subtype, String args) {
        this.subtype = subtype;
        arguments = args;
    }
}
