package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EVENT_INDEX_DESC_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.HOURS_DESC_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_EVENT_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_MEMBER_INDEX_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.MEMBER_INDEX_DESC_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.REMARK_DESC_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_AURORA;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.EditEnrolmentCommand;
import seedu.ccacommander.logic.commands.EditEnrolmentCommand.EditEnrolmentDescriptor;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.testutil.EditEnrolmentDescriptorBuilder;

public class EditEnrolmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEnrolmentCommand.MESSAGE_USAGE);

    private EditEnrolmentCommandParser parser = new EditEnrolmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_HOURS_AURORA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO,
                EditEnrolmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_MEMBER_INDEX_DESC + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid memberIndex
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + INVALID_EVENT_INDEX_DESC + REMARK_DESC_AURORA,
                ParserUtil.MESSAGE_INVALID_INDEX); // invalid eventIndex
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC,
                Hours.MESSAGE_CONSTRAINTS); // invalid hours
        assertParseFailure(parser, MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS); // invalid remark

        // invalid hours followed by valid remark
        assertParseFailure(parser,
                MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC + REMARK_DESC_AURORA,
                Hours.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC + INVALID_REMARK_DESC,
                Hours.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + REMARK_DESC_AURORA;

        EditEnrolmentDescriptor descriptor =
                new EditEnrolmentDescriptorBuilder()
                        .withHours(VALID_HOURS_AURORA)
                        .withRemark(VALID_REMARK_AURORA)
                        .build();
        EditEnrolmentCommand expectedCommand =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA;

        EditEnrolmentDescriptor descriptor = new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_AURORA).build();
        EditEnrolmentCommand expectedCommand =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // EnrolCommandParserTest#parse_repeatedValue_failure

        // invalid followed by valid
        String userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC + HOURS_DESC_AURORA;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // valid followed by invalid
        userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + INVALID_HOURS_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // multiple valid fields repeated
        userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + HOURS_DESC_AURORA + REMARK_DESC_AURORA
                + HOURS_DESC_AURORA + REMARK_DESC_AURORA;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS, PREFIX_REMARK));

        // multiple invalid values
        userInput = MEMBER_INDEX_DESC_ONE + EVENT_INDEX_DESC_TWO + INVALID_HOURS_DESC + INVALID_REMARK_DESC
                + INVALID_HOURS_DESC + INVALID_REMARK_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS, PREFIX_REMARK));
    }
}
