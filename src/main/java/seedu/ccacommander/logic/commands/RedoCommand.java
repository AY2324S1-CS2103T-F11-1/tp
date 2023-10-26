package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;

/**
 * Redoes the latest redoable command {@code Command}
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS_REDO = "Command successfully redone: \n%s";
    public static final String MESSAGE_NO_AVAILABLE_COMMAND = "No available commands to redo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_NO_AVAILABLE_COMMAND);
        }

        String redoCommandMessage = model.redo();
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_REDO, redoCommandMessage));
    }
}