package seedu.duke.ui;

/**
 * Adopted from IP of ZT712002
 */

import java.util.Scanner;
/**
 * Ui class handles user interactions, including input and output.
 * @param IN Scanner object for reading user input
 * @param isActive boolean flag to control the main loop
 * @param userInput String to store the latest user input
 *
 */
public class Ui {
    private final Scanner IN;
    private boolean isActive;
    private String userInput;



    public String getUserInput() {
        return userInput;
    }

    /**
     * Constructor for Ui class. Initializes the Scanner and sets the active flag to true.
     */
    public Ui() {
        this.IN = new Scanner(System.in);
        this.isActive = true;
    }

    public void setUserInput() {
        this.userInput = IN.nextLine();
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
