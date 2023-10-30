package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Enrols a member to an event in CcaCommander.
 */
public class EnrolCommand extends Command {

    public static final String COMMAND_WORD = "enrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrols a member to an event in CCACommander. \n"
            + "Parameters: "
            + PREFIX_MEMBER + "MEMBER_INDEX "
            + PREFIX_EVENT + "EVENT_INDEX "
            + PREFIX_HOURS + "HOURS "
            + PREFIX_REMARK + "REMARK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER + "1 "
            + PREFIX_EVENT + "1 "
            + PREFIX_HOURS + "0 "
            + PREFIX_REMARK + "Role: Supervisor";


    public static final String MESSAGE_SUCCESS = "Successfully added: %1$s";
    public static final String MESSAGE_COMMIT = "Successfully enrolled: %1$s";
    public static final String MESSAGE_DUPLICATE_ENROLMENT = "This member has already been enrolled to the event. ";

    private final Index memberIndex;
    private final Index eventIndex;
    private final Hours hours;
    private final Remark remark;

    /**
     * Creates an EnrolCommand to add the specified {@code Enrolment}
     */
    public EnrolCommand(Index memberIndex, Index eventIndex, Hours hours, Remark remark) {
        requireNonNull(memberIndex);
        requireNonNull(eventIndex);
        requireNonNull(hours);
        requireNonNull(remark);

        this.memberIndex = memberIndex;
        this.eventIndex = eventIndex;
        this.hours = hours;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownMemberList = model.getFilteredMemberList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (memberIndex.getZeroBased() >= lastShownMemberList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Member member = lastShownMemberList.get(memberIndex.getZeroBased());
        Event event = lastShownEventList.get(eventIndex.getZeroBased());

        Name memberName = member.getName();
        Name eventName = event.getName();

        Enrolment toAdd = new Enrolment(memberName, eventName, this.hours, this.remark);

        if (model.hasEnrolment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENROLMENT);
        }

        model.createEnrolment(toAdd);
        model.commit(String.format(MESSAGE_COMMIT, toAdd.getMemberAndEventEnrolment()));
        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(false);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EnrolCommand)) {
            return false;
        }

        EnrolCommand otherEnrolCommand = (EnrolCommand) other;
        return memberIndex.equals(otherEnrolCommand.memberIndex)
                && eventIndex.equals(otherEnrolCommand.eventIndex)
                && hours.equals(otherEnrolCommand.hours)
                && remark.equals(otherEnrolCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("member index", memberIndex)
                .add("event index", eventIndex)
                .add("hours", hours)
                .add("remark", remark)
                .toString();
    }
}
