package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.SameEventPredicate;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.MemberInNameCollectionPredicate;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Lists all members of an event to the user
 */
public class ViewEventCommand extends Command {
    public static final String COMMAND_WORD = "viewEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the members of the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Listed all members in %1$s.";

    private final Index targetIndex;

    /**
     * Creates a ViewEventCommand to list all members of the event displayed at the specified index.
     */
    public ViewEventCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Member> lastShownMemberList = model.getFilteredMemberList();
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Enrolment> enrolmentList = model.getFilteredEnrolmentList();
        if (targetIndex.getOneBased() > lastShownEventList.size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }


        Event event = lastShownEventList.get(targetIndex.getZeroBased());
        Name eventName = event.getName();


        // loop through enrolment list, check if each enrolment.getEventName() = event.getName()
        // then add enrolment.getName() to
        // Collection<Name> memberNames
        Collection<Name> namesCollection = new HashSet<>();
        for (Enrolment enrolment: enrolmentList) {
            if (enrolment.getEventName().equals(eventName)) {
                Name memName = enrolment.getMemberName();
                namesCollection.add(memName);
                for (Member member: lastShownMemberList) {
                    if (member.getName().equals(memName)) {
                        member.setHours(enrolment.getHours());
                        member.setRemark(enrolment.getRemark());
                    }
                }
            }

        }

        MemberListPanel.setIsViewEventCommand(true);
        EventListPanel.setIsViewMemberCommand(false);
        model.updateFilteredMemberList(new MemberInNameCollectionPredicate(namesCollection));
        model.updateFilteredEventList(new SameEventPredicate(event));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(event)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewEventCommand // instanceof handles nulls
                && targetIndex.equals(((ViewEventCommand) other).targetIndex));
    }
}
