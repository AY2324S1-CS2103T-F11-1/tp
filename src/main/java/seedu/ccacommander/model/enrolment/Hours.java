package seedu.ccacommander.model.enrolment;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of hours a Member has gone for an Event.
 * Guarantees: immutable; is valid as declared in {@link #isValidHours(String)}
 */
public class Hours {

    public static final Hours EMPTY_HOURS = new Hours("0");
    public static final String MESSAGE_CONSTRAINTS =
            "Hours should be a non-negative integer up to 2147483647 and it should not be blank";

    public final Integer value;

    /**
     * Constructs a {@code Hours}.
     *
     * @param hours A valid number of hours in String.
     */
    public Hours(String hours) {
        requireNonNull(hours);
        checkArgument(isValidHours(hours), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(hours);
    }

    /**
     * Returns true if a given number of hours is a valid hours.
     */
    public static boolean isValidHours(String test) {
        try {
            Integer i = Integer.valueOf(test);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Hours)) {
            return false;
        }

        Hours otherDate = (Hours) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
