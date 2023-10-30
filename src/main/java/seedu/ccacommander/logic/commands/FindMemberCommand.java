package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.member.MemberNameContainsKeywordsPredicate;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Finds and lists all members in CcaCommander whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMemberCommand extends Command {

    public static final String COMMAND_WORD = "findMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all members whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final MemberNameContainsKeywordsPredicate predicate;

    public FindMemberCommand(MemberNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemberList(predicate);
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(false);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW, model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMemberCommand)) {
            return false;
        }

        FindMemberCommand otherFindMemberCommand = (FindMemberCommand) other;
        return predicate.equals(otherFindMemberCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
