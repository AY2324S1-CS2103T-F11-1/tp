package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EVENT_INDEX_DESC_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.HOURS_DESC_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_EVENT_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_MEMBER_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.REMARK_DESC_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_AURORA;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.EnrolCommand;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;

public class EnrolCommandParserTest {
    private EnrolCommandParser parser = new EnrolCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        EnrolCommand expectedEnrolEventCommand =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_TWO,
                        Optional.of(VALID_HOURS_A), Optional.of(VALID_REMARK_A));

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + MEMBER_INDEX_DESC_ONE
                        + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + REMARK_DESC_AURORA,
                expectedEnrolEventCommand);
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedEnrolmentString = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA
                + REMARK_DESC_AURORA;

        // multiple member indexes
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));

        // multiple event indexes
        assertParseFailure(parser, EVENT_INDEX_DESC_TWO + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));

        // multiple hours
        assertParseFailure(parser, HOURS_DESC_AURORA + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_AURORA + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedEnrolmentString + MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO
                        + HOURS_DESC_AURORA + REMARK_DESC_AURORA + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER, PREFIX_EVENT, PREFIX_HOURS, PREFIX_REMARK));

        // invalid value followed by valid value

        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));

        // invalid event index
        assertParseFailure(parser, INVALID_EVENT_INDEX_DESC + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));

        // invalid hours
        assertParseFailure(parser, INVALID_HOURS_DESC + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // invalid remark
        assertParseFailure(parser, INVALID_REMARK_DESC + validExpectedEnrolmentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // valid value followed by invalid value

        // invalid member index
        assertParseFailure(parser, validExpectedEnrolmentString + INVALID_MEMBER_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEMBER));

        // invalid event index
        assertParseFailure(parser, validExpectedEnrolmentString + INVALID_EVENT_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT));

        // invalid hours
        assertParseFailure(parser, validExpectedEnrolmentString + INVALID_HOURS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // invalid remark
        assertParseFailure(parser, validExpectedEnrolmentString + INVALID_REMARK_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrolCommand.MESSAGE_USAGE);

        // missing name index field
        assertParseFailure(parser, EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + REMARK_DESC_AURORA,
                expectedMessage);

        // missing event index field
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + HOURS_DESC_AURORA + REMARK_DESC_AURORA,
                expectedMessage);

    }

    @Test
    public void parse_optionalFieldMissing_success() {
        // missing hours
        EnrolCommand expectedEnrolCommandNoHours = new EnrolCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT,
                Optional.empty(), Optional.of(new Remark(VALID_REMARK_AURORA)));
        String userInputNoHours = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + REMARK_DESC_AURORA;
        assertParseSuccess(parser, userInputNoHours, expectedEnrolCommandNoHours);

        // missing remark
        EnrolCommand expectedEnrolCommandNoRemarks = new EnrolCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT,
                Optional.of(new Hours(VALID_HOURS_AURORA)), Optional.empty());
        String userInputNoRemarks = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA;
        assertParseSuccess(parser, userInputNoRemarks, expectedEnrolCommandNoRemarks);

        // missing hours and remark
        EnrolCommand expectedEnrolCommandNoHoursAndRemarks = new EnrolCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT,
                Optional.empty(), Optional.empty());
        String userInputNoHoursAndRemarks = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO;
        assertParseSuccess(parser, userInputNoHoursAndRemarks, expectedEnrolCommandNoHoursAndRemarks);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid member index
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA
                + REMARK_DESC_AURORA, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid event index
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + INVALID_EVENT_INDEX_DESC + HOURS_DESC_AURORA
                + REMARK_DESC_AURORA, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid hours
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC
                        + REMARK_DESC_AURORA, Hours.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA
                        + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA
                        + INVALID_REMARK_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_INDEX_DESC_ONE
                        + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + REMARK_DESC_AURORA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrolCommand.MESSAGE_USAGE));
    }
}
