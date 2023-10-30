package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.commands.EnrolCommand;
import seedu.ccacommander.logic.parser.exceptions.ParseException;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;

/**
 * Parses input arguments and creates a new EnrolCommand object
 */
public class EnrolCommandParser implements Parser<EnrolCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EnrolCommand
     * and returns an EnrolCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnrolCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_EVENT, PREFIX_HOURS, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrolCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_EVENT, PREFIX_HOURS, PREFIX_REMARK);
        Index memberIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());
        Hours hours = getParsedHour(argMultimap);
        Remark remark = getParsedRemark(argMultimap);

        return new EnrolCommand(memberIndex, eventIndex, Optional.ofNullable(hours), Optional.ofNullable(remark));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private Hours getParsedHour(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_HOURS).isPresent()) {
            return ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
        } else {
            return null;
        }
    }

    private Remark getParsedRemark(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            return ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        } else {
            return null;
        }
    }

}
