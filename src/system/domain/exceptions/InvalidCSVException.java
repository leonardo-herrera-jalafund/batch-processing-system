package system.domain.exceptions;

public class InvalidCSVException extends RuntimeException {
    public InvalidCSVException(String message) {
        super(message);
    }
}
