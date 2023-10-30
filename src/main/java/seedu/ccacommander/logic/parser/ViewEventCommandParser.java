package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.commands.ViewEventCommand;
import seedu.ccacommander.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new ViewEventCommand object
 */
public class ViewEventCommandParser implements Parser<ViewEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewEventCommand
     * and returns a ViewEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
