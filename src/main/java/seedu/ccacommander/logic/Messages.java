package seedu.ccacommander.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ccacommander.logic.parser.Prefix;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX = "The member index provided is invalid";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d members listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_MEMBER_AND_EVENT_DISPLAYED_INDEX = "The member and event indexes "
            + "provided are both invalid";
    public static final String MESSAGE_ENROLMENT_NOT_FOUND = "The member is not enroled to the event!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code member} for display to the user.
     */
    public static String format(Member member) {
        final StringBuilder builder = new StringBuilder();
        builder.append(member.getName())
                .append(" | Phone: ")
                .append(member.getPhone())
                .append(" | Email: ")
                .append(member.getEmail())
                .append(" | Address: ")
                .append(member.getAddress())
                .append(" | Tags: ");
        member.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append(" | Date: ")
                .append(event.getDate())
                .append(" | Location: ")
                .append(event.getLocation())
                .append(" | Tags: ");
        event.getTags().forEach(tag -> builder.append(tag).append(" "));
        return builder.toString();
    }

    /**
     * Formats the {@code enrolment} for display to the user.
     */
    public static String format(Enrolment enrolment) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Member: ")
                .append(enrolment.getMemberName())
                .append(" to Event: ")
                .append(enrolment.getEventName())
                .append(" | Hours: ")
                .append(enrolment.getHours())
                .append(" | Remark: ")
                .append(enrolment.getRemark());
        return builder.toString();
    }
}
