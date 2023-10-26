package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.member.Member;

/**
 * Deletes a member identified using its displayed index from CcaCommander.
 */
public class DeleteMemberCommand extends Command {
    public static final String COMMAND_WORD = "deleteMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member identified by the index number used in the displayed member list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_MEMBER_SUCCESS = "Deleted Member: %1$s";
    public static final String MESSAGE_COMMIT = "Deleted Member: %1$s.";
    private final Index targetIndex;

    public DeleteMemberCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        Member memberToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMember(memberToDelete);
        model.commit(String.format(MESSAGE_COMMIT, memberToDelete.getName()));
        return new CommandResult(String.format(MESSAGE_DELETE_MEMBER_SUCCESS, Messages.format(memberToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMemberCommand)) {
            return false;
        }

        DeleteMemberCommand otherDeleteMemberCommand = (DeleteMemberCommand) other;
        return targetIndex.equals(otherDeleteMemberCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
