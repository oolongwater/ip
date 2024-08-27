package exception;

/**
 * Exception thrown when user input is invalid in the Giorgo application.
 */
public class InvalidInputException extends Exception {

    /**
     * Constructs a new InvalidInputException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidInputException(String message) {
        super(message);
        System.out.println("ERROR");
    }
}
