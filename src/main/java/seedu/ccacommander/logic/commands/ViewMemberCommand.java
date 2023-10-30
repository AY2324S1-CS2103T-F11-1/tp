package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.EventInNameCollectionPredicate;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.SameMemberPredicate;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Lists all events of a member to the user
 */
public class ViewMemberCommand extends Command {
    public static final String COMMAND_WORD = "viewMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the events of the member identified by the index number used in the displayed member list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Listed all events of %1$s.";

    private final Index targetIndex;

    /**
     * Creates a ViewMemberCommand to list all events of the member displayed at the specified index.
     */
    public ViewMemberCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Member> lastShownMemberList = model.getFilteredMemberList();
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Enrolment> enrolmentList = model.getFilteredEnrolmentList();
        if (targetIndex.getOneBased() > lastShownMemberList.size()) {
            throw new CommandException(MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }


        Member member = lastShownMemberList.get(targetIndex.getZeroBased());
        Name memberName = member.getName();

        // View all events of member
        // loop through enrolment list, check if each enrolment.getMemberName() = member.getName()
        // then add enrolment.event.getName() to
        // Collection<Name> eventNames
        Collection<Name> eventNamesCollection = new HashSet<>();
        for (Enrolment enrolment: enrolmentList) {
            if (enrolment.getMemberName().equals(memberName)) {
                Name eventName = enrolment.getEventName();
                eventNamesCollection.add(eventName);
                for (Event event: lastShownEventList) {
                    if (event.getName().equals(eventName)) {
                        event.setHours(enrolment.getHours());
                        event.setRemark(enrolment.getRemark());
                    }
                }
            }
        }

        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(true);
        model.updateFilteredMemberList(new SameMemberPredicate(member));
        model.updateFilteredEventList(new EventInNameCollectionPredicate(eventNamesCollection));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(member)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewMemberCommand
                && targetIndex.equals(((ViewMemberCommand) other).targetIndex));
    }
}
