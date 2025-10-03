package seedu.duke.command;

public class Command {
    private String commandType;
    private String commandSubtype;
    private String arguments;
    public Command(String CommandType, String CommandSubtype, String Arguments) {
        this.commandType = CommandType;
        this.commandSubtype = CommandSubtype;
        this.arguments = Arguments;

    }
}
