package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.Model;

/**
 * Clears the CcaCommander book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "CCACommander has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCcaCommander(new CcaCommander());
        model.commit(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
