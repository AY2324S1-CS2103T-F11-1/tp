package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.model.ModelManager.findEnrolmentFromList;

import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.exceptions.EnrolmentNotFoundException;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.shared.Name;

/**
 * Deletes an enrolment identified using the displayed indexes of the member and event from CcaCommander.
 */
public class UnenrolCommand extends Command {
    public static final String COMMAND_WORD = "unenrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the enrolment identified by the member's index number used in the displayed member list "
            + "and the event's index number used in the displayed event list.\n"
            + "Parameters: MEMBER_INDEX (must be a positive integer), EVENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " m/1" + " e/1";
    public static final String MESSAGE_DELETE_ENROLMENT_SUCCESS = "Deleted Enrolment: %1$s";
    public static final String MESSAGE_COMMIT = "Deleted Enrolment: %1$s.";
    private final Index memberIndex;
    private final Index eventIndex;

    /**
     * Creates an UnenrolCommand to remove the specified {@code Enrolment}
     */
    public UnenrolCommand(Index memberIndex, Index eventIndex) {
        this.memberIndex = memberIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Enrolment> lastShownEnrolmentList = model.getFilteredEnrolmentList();
        List<Member> lastShownMemberList = model.getFilteredMemberList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        int zeroBasedMemberIndex = memberIndex.getZeroBased();
        int zeroBasedEventIndex = eventIndex.getZeroBased();

        if (zeroBasedMemberIndex >= lastShownMemberList.size()
                && zeroBasedEventIndex < lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        } else if (zeroBasedEventIndex >= lastShownEventList.size()
                && zeroBasedMemberIndex < lastShownMemberList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        } else if (zeroBasedMemberIndex >= lastShownMemberList.size()
                && zeroBasedEventIndex >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_AND_EVENT_DISPLAYED_INDEX);
        }

        Member member = lastShownMemberList.get(zeroBasedMemberIndex);
        Event event = lastShownEventList.get(zeroBasedEventIndex);

        Name memberName = member.getName();
        Name eventName = event.getName();
        Enrolment enrolmentToDelete;

        try {
            enrolmentToDelete = findEnrolmentFromList(lastShownEnrolmentList, memberName, eventName);
        } catch (EnrolmentNotFoundException ee) {
            throw new CommandException(Messages.MESSAGE_ENROLMENT_NOT_FOUND);
        }

        assert enrolmentToDelete != null : "The enrolment to delete should be null";
        model.deleteEnrolment(enrolmentToDelete);
        model.commit(String.format(MESSAGE_COMMIT, enrolmentToDelete.getMemberAndEventEnrolment()));
        return new CommandResult(String.format(MESSAGE_DELETE_ENROLMENT_SUCCESS, Messages.format(enrolmentToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnenrolCommand)) {
            return false;
        }

        UnenrolCommand otherUnenrolCommand = (UnenrolCommand) other;
        return memberIndex.equals(otherUnenrolCommand.memberIndex)
                && eventIndex.equals(otherUnenrolCommand.eventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("memberIndex", memberIndex)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
