package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;

/**
 * Undoes the latest undoable command {@code Command}
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS_UNDO = "Previous command successfully undone: \n%s";
    public static final String MESSAGE_NO_AVAILABLE_COMMAND = "No available commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_NO_AVAILABLE_COMMAND);
        }

        String undoCommandMessage = model.undo();
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_UNDO, undoCommandMessage));
    }
}
