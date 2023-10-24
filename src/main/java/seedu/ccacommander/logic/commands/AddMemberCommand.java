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
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.model.attendance.Hours;
import seedu.ccacommander.model.attendance.Remark;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.shared.Name;

/**
 * Adds a member to an event in CcaCommander.
 */
public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "addMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to an event in CCACommander. \n"
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
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "This member has already been added to the event. ";

    private final Index memberIndex;
    private final Index eventIndex;
    private final Hours hours;
    private final Remark remark;

    /**
     * Creates an CreateEventCommand to add the specified {@code Event}
     */
    public AddMemberCommand(Index memberIndex, Index eventIndex, Hours hours, Remark remark) {
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

        Attendance toAdd = new Attendance(memberName, eventName, this.hours, this.remark);

        if (model.hasAttendance(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }

        model.createAttendance(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMemberCommand)) {
            return false;
        }

        AddMemberCommand otherAddMemberCommand = (AddMemberCommand) other;
        return memberIndex.equals(otherAddMemberCommand.memberIndex)
                && eventIndex.equals(otherAddMemberCommand.eventIndex)
                && hours.equals(otherAddMemberCommand.hours)
                && remark.equals(otherAddMemberCommand.remark);
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
