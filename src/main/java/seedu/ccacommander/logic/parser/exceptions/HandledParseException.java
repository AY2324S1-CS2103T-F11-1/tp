package seedu.ccacommander.logic.parser.exceptions;

/**
 * Represents a rethrown ParseException when a parser encounters a problem.
 * This class was created to counter the issue when a caller method calls the called method which throws
 * a new ParseException.
 * In the called method, a ParseException is thrown and caught within itself.
 * Upon doing so, the called method throws a new ParseException.
 * However, when the caller method catches the ParseException, it catches the original ParseException instead of
 * the new ParseException, which is not the behaviour we want.
 * Hence, HandledParseException is created and will be the exception type of the new ParseException,
 * allowing for better differentiation between the old and new ParseExceptions.
 */
public class HandledParseException extends ParseException {

    private String hpeMessage;

    /**
     * Creates a new HandledParseException.
     * @param message
     */
    public HandledParseException(String message) {
        super(message);
        this.hpeMessage = message;
    }

    /**
     * Creates a new HandledParseException.
     * @param message
     * @param cause
     */
    public HandledParseException(String message, Throwable cause) {
        super(message, cause);
        this.hpeMessage = message;
    }

    @Override
    public String getMessage() {
        return this.hpeMessage;
    }
}
