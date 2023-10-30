package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Deletes an event identified using its displayed index from CcaCommander.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_COMMIT = "Deleted Event: %1$s";
    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        model.commit(String.format(MESSAGE_COMMIT, eventToDelete.getName()));

        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(false);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(eventToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherDeleteEventCommand = (DeleteEventCommand) other;
        return targetIndex.equals(otherDeleteEventCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
