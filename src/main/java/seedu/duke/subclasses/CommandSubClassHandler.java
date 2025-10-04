package seedu.duke.subclasses;

public class CommandSubClassHandler {
    private static String subclass;
    public static String getSubClass(){
        return subclass;
    }
    public void setSubclass(CommandSubClass commandSubClass) {
        switch (commandSubClass) {
        case CLIENT:
                subclass =  "client";
                break;
        case MEETING:
            subclass = "meeting";
            break;
        case POLICY:
            subclass = "policy";
            break;
        }

    }
}
