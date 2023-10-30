package seedu.ccacommander.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.commands.UnenrolCommand;
import seedu.ccacommander.logic.parser.exceptions.HandledParseException;
import seedu.ccacommander.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnenrolCommand object
 */
public class UnenrolCommandParser implements Parser<UnenrolCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnenrolCommand
     * and returns an UnenrolCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnenrolCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index memberIndex;
        Index eventIndex;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrolCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_EVENT);
        try {
            memberIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
        } catch (ParseException pe) {
            throw new HandledParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnenrolCommand.MESSAGE_USAGE), pe);
        }

        return new UnenrolCommand(memberIndex, eventIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
