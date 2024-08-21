package exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
        System.out.println("ERROR");
    }
}