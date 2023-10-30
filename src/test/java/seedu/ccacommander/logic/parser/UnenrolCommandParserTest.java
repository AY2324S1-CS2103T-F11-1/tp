package seedu.ccacommander.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EVENT_INDEX_DESC_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_EVENT_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_MEMBER_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_EVENT_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_MEMBER_INDEX_ONE;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.UnenrolCommand;
import seedu.ccacommander.logic.parser.exceptions.HandledParseException;
import seedu.ccacommander.logic.parser.exceptions.ParseException;

public class UnenrolCommandParserTest {
    private UnenrolCommandParser parser = new UnenrolCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        UnenrolCommand expectedUnenrolEventCommand =
                new UnenrolCommand(VALID_INDEX_ONE, VALID_INDEX_TWO);

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + MEMBER_INDEX_DESC_ONE
                        + EVENT_INDEX_DESC_TWO, expectedUnenrolEventCommand);
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedEnrolmentString = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrolCommand.MESSAGE_USAGE);

        // multiple member indexes
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));

        // multiple event indexes
        assertParseFailure(parser, EVENT_INDEX_DESC_TWO + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedEnrolmentString + MEMBER_INDEX_DESC_ONE
                        + EVENT_INDEX_DESC_TWO + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER, PREFIX_EVENT));

        // invalid value followed by valid value

        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + EVENT_INDEX_DESC_TWO,
                expectedMessage);

        // invalid event index
        assertParseFailure(parser, INVALID_EVENT_INDEX_DESC + MEMBER_INDEX_DESC_ONE,
                expectedMessage);

        // valid value followed by invalid value

        // invalid member index
        assertParseFailure(parser, EVENT_INDEX_DESC_TWO + INVALID_MEMBER_INDEX_DESC,
                expectedMessage);

        // invalid event index
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + INVALID_EVENT_INDEX_DESC,
                expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrolCommand.MESSAGE_USAGE);

        // missing name index prefix
        assertParseFailure(parser, EVENT_INDEX_DESC_TWO, expectedMessage);

        // missing event index prefix
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MEMBER_INDEX_ONE + VALID_EVENT_INDEX_TWO, expectedMessage);
    }

    @Test
    public void parse_excessArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrolCommand.MESSAGE_USAGE);

        try {
            parser.parse(MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + " a");
        } catch (ParseException pe) {
            // ParseException object is cast to HandledParseException to extract message out of rethrown exception.
            // Otherwise, the message from the original exception within the parse method of UnenrolCommandParser
            // will be printed here, which is not what we want.
            HandledParseException hpe = (HandledParseException) pe;
            assertEquals(expectedMessage, hpe.getMessage());
        }

        try {
            parser.parse(MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + " 1");
        } catch (ParseException pe) {
            HandledParseException hpe = (HandledParseException) pe;
            assertEquals(expectedMessage, hpe.getMessage());
        }

        try {
            parser.parse(MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + " a/This is an extra field!");
        } catch (ParseException pe) {
            HandledParseException hpe = (HandledParseException) pe;
            assertEquals(expectedMessage, hpe.getMessage());
        }
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrolCommand.MESSAGE_USAGE);
        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + EVENT_INDEX_DESC_TWO,
                expectedMessage);

        // invalid event index
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + INVALID_EVENT_INDEX_DESC,
                expectedMessage);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + INVALID_EVENT_INDEX_DESC,
                expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_INDEX_DESC_ONE
                        + EVENT_INDEX_DESC_TWO, expectedMessage);
    }
}
