package base;

public class InvalidUsageOrConfig extends RuntimeException {
    public InvalidUsageOrConfig(String message) {
        super(message);
    }
}
