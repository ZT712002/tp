package seedu.duke.exception;
/**
 * Represents an exception specific to the FinanceProPlus application.
 * This exception is thrown when an error related to FinanceProPlus occurs.
 */
public class FinanceProPlusException extends Exception {
    public FinanceProPlusException() {
    }
    /**
     * Constructs a new FinanceProPlusException with the specified detail message.
     *
     * @param message the detail message
     */
    public FinanceProPlusException(String message) {
        super(message);
    }
}
