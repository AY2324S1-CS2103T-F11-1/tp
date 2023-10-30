package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ccacommander.commons.core.LogsCenter;
import seedu.ccacommander.logic.commands.ClearCommand;
import seedu.ccacommander.logic.commands.Command;
import seedu.ccacommander.logic.commands.CreateEventCommand;
import seedu.ccacommander.logic.commands.CreateMemberCommand;
import seedu.ccacommander.logic.commands.DeleteEventCommand;
import seedu.ccacommander.logic.commands.DeleteMemberCommand;
import seedu.ccacommander.logic.commands.EditEventCommand;
import seedu.ccacommander.logic.commands.EditMemberCommand;
import seedu.ccacommander.logic.commands.EnrolCommand;
import seedu.ccacommander.logic.commands.ExitCommand;
import seedu.ccacommander.logic.commands.FindEventCommand;
import seedu.ccacommander.logic.commands.FindMemberCommand;
import seedu.ccacommander.logic.commands.HelpCommand;
import seedu.ccacommander.logic.commands.ListCommand;
import seedu.ccacommander.logic.commands.RedoCommand;
import seedu.ccacommander.logic.commands.UndoCommand;
import seedu.ccacommander.logic.commands.ViewEventCommand;
import seedu.ccacommander.logic.commands.ViewMemberCommand;
import seedu.ccacommander.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CcaCommanderParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(CcaCommanderParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case CreateMemberCommand.COMMAND_WORD:
            return new CreateMemberCommandParser().parse(arguments);

        case CreateEventCommand.COMMAND_WORD:
            return new CreateEventCommandParser().parse(arguments);

        case EditMemberCommand.COMMAND_WORD:
            return new EditMemberCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case DeleteMemberCommand.COMMAND_WORD:
            return new DeleteMemberCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case EnrolCommand.COMMAND_WORD:
            return new EnrolCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindMemberCommand.COMMAND_WORD:
            return new FindMemberCommandParser().parse(arguments);

        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

        case ViewMemberCommand.COMMAND_WORD:
            return new ViewMemberCommandParser().parse(arguments);

        case ViewEventCommand.COMMAND_WORD:
            return new ViewEventCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
