package seedu.ccacommander.logic.parser;

import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ccacommander.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.ccacommander.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.ccacommander.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ccacommander.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ccacommander.testutil.TypicalMembers.AMY;
import static seedu.ccacommander.testutil.TypicalMembers.BOB;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.CreateMemberCommand;
import seedu.ccacommander.model.member.Address;
import seedu.ccacommander.model.member.Email;
import seedu.ccacommander.model.member.Gender;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.Phone;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.model.tag.Tag;
import seedu.ccacommander.testutil.MemberBuilder;

public class CreateMemberCommandParserTest {
    private CreateMemberCommandParser parser = new CreateMemberCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Member expectedMember = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new CreateMemberCommand(expectedMember));


        // multiple tags - all accepted
        Member expectedMemberMultipleTags = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new CreateMemberCommand(expectedMemberMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple genders
        assertParseFailure(parser, GENDER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + GENDER_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_GENDER, PREFIX_ADDRESS,
                        PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid gender
        assertParseFailure(parser, INVALID_GENDER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid gender
        assertParseFailure(parser, validExpectedPersonString + INVALID_GENDER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Member expectedMemberNoTags = new MemberBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new CreateMemberCommand(expectedMemberNoTags));

        // missing phone
        Member expectedMemberNoPhone = new MemberBuilder(AMY).withPhone().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND,
                new CreateMemberCommand(expectedMemberNoPhone));

        // missing email
        Member expectedMemberNoEmail = new MemberBuilder(AMY).withEmail().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY + PHONE_DESC_AMY
                        + ADDRESS_DESC_AMY + TAG_DESC_FRIEND,
                new CreateMemberCommand(expectedMemberNoEmail));

        // missing address
        Member expectedMemberNoAddress = new MemberBuilder(AMY).withAddress().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + TAG_DESC_FRIEND,
                new CreateMemberCommand(expectedMemberNoAddress));

        // missing all optional fields
        Member expectedMemberNoOptionalFields = new MemberBuilder(AMY).withPhone()
                .withEmail().withAddress().withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY,
                new CreateMemberCommand(expectedMemberNoOptionalFields));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateMemberCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_GENDER_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Gender.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + GENDER_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateMemberCommand.MESSAGE_USAGE));
    }
}
