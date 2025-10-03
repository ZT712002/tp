package seedu.duke;

import seedu.duke.ui.Ui;

import java.util.Scanner;

public class FinanceProPlus {
    private Ui ui;
    public FinanceProPlus() {
        ui = new Ui();
    }
    public void run(){
        ui.printWelcomeMessage();
        boolean runLoop = ui.getIsActive();
        while(runLoop){
            ui.setUserInput();
            String unprocessedInput = ui.getUserInput();
        }
    }
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
    }
}
