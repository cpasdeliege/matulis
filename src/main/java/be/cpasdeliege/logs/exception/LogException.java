package be.cpasdeliege.logs.exception;

public class LogException extends RuntimeException {
	public LogException() {
        super();
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }
}
