package seedu.ccacommander.model.enrolment.exceptions;

/**
 * Signals that the operation is unable to find the specified enrolment.
 */
public class EnrolmentNotFoundException extends RuntimeException {
    public EnrolmentNotFoundException() {
        super("Enrolment cannot be found");
    }
}
