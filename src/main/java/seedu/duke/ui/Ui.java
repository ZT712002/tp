package seedu.duke.ui;

import java.util.Scanner;
/**
 * Ui class handles user interactions, including input and output.
 * Adopted from IP of ZT712002
 */
public class Ui {
    private  Scanner in;
    private boolean isActive;
    private String userInput;


    /**
     * Constructor for Ui class. Initializes the Scanner and sets the active flag to true.
     */
    public Ui() {
        this.in = new Scanner(System.in);
        this.isActive = true;
    }
    public String getUserInput() {
        return userInput;
    }
    public void setUserInput() {
        this.userInput = in.nextLine();
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void printWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    public void printLineDivider() {
        System.out.println("*************************************");
    }
    public void printGoodbyeMessage() {
        System.out.println("Bye. See you next time!");
    }

}
