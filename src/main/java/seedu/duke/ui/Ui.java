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
    public void closeScanner() {
        in.close();
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void printWelcomeMessage() {
        printLineDivider();
        System.out.println("Welcome to FinanceProPlus!");
        System.out.println("Your personal finance management assistant.");
        printLineDivider();
        System.out.println("New user? Here are some commands to get you started:");
        System.out.println("  1. 'client add n/<NAME> c/<CONTACT> id/<NRIC> p/<POLICY>' - Add a new client");
        System.out.println("  2. 'list client' - View all your clients");
        System.out.println("  3. 'list meeting' - View all your meetings");
        System.out.println("  4. 'list policy' - View all your policies");
        System.out.println("  5. 'exit' - Exit the application");
        System.out.println("Tip: Start by adding a client with the full details!");
        printLineDivider();
        System.out.println("What is your command?");
    }

    public void printLineDivider() {
        System.out.println("*************************************");
    }
    public void printGoodbyeMessage() {
        System.out.println("Bye. See you next time!");
    }

}
