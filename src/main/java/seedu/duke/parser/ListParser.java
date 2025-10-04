package seedu.duke.parser;

public class ListParser extends Parser{
    private String commandSubtype;
    private String arguments;
    public ListParser(String Arguments, String commandArgs) {
        String[] commandParts = splitCommand(Arguments);
        this.commandSubtype = commandParts[0];
        this.arguments = commandParts[1];
    }
}
