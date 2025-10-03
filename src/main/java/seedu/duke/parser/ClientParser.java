package seedu.duke.parser;

public class ClientParser extends Parser{
    private String commandSubtype;
    private String arguments;
    public ClientParser(String Arguments) {
        String[] commandParts = splitCommand(Arguments);
        this.commandSubtype = commandParts[0];
        this.arguments = commandParts[1];
    }
}
