package base;

public class InvalidPageStateException extends RuntimeException {
    public InvalidPageStateException(String message) {
        super(message);
    }
}
