package seedu.ccacommander.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalEnrolments.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalEnrolments.BENSON_BOXING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.EventNameContainsKeywordsPredicate;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.MemberNameContainsKeywordsPredicate;
import seedu.ccacommander.testutil.EditEventDescriptorBuilder;
import seedu.ccacommander.testutil.EditMemberDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_GENDER_AMY = "Female";
    public static final String VALID_GENDER_BOB = "Male";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_AURORA = "Aurora Borealis";
    public static final String VALID_NAME_BOXING = "Boxing Day";
    public static final String VALID_DATE_AURORA = "2023-11-30";
    public static final String VALID_DATE_BOXING = "2023-12-26";
    public static final String VALID_LOCATION_AURORA = "Greenland";
    public static final String VALID_LOCATION_BOXING = "Ridge View Residential College";
    public static final String VALID_TAG_AURORA = "nature";
    public static final String VALID_TAG_BOXING = "rvrc";

    public static final String VALID_MEMBER_INDEX_ONE = "1";
    public static final String VALID_EVENT_INDEX_TWO = "2";
    public static final String VALID_HOURS_AURORA = "3";
    public static final String VALID_HOURS_BOXING = "7";
    public static final String VALID_REMARK_AURORA = "Role: Photographer";
    public static final String VALID_REMARK_BOXING = "Bring boxing gloves";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String NAME_DESC_AURORA = " " + PREFIX_NAME + VALID_NAME_AURORA;
    public static final String NAME_DESC_BOXING = " " + PREFIX_NAME + VALID_NAME_BOXING;
    public static final String DATE_DESC_AURORA = " " + PREFIX_DATE + VALID_DATE_AURORA;
    public static final String DATE_DESC_BOXING = " " + PREFIX_DATE + VALID_DATE_BOXING;
    public static final String LOCATION_DESC_AURORA = " " + PREFIX_LOCATION + VALID_LOCATION_AURORA;
    public static final String LOCATION_DESC_BOXING = " " + PREFIX_LOCATION + VALID_LOCATION_BOXING;
    public static final String MEMBER_INDEX_DESC_ONE = " " + PREFIX_MEMBER + VALID_MEMBER_INDEX_ONE;
    public static final String EVENT_INDEX_DESC_TWO = " " + PREFIX_EVENT + VALID_EVENT_INDEX_TWO;
    public static final String HOURS_DESC_AURORA = " " + PREFIX_HOURS + VALID_HOURS_AURORA;
    public static final String REMARK_DESC_AURORA = " " + PREFIX_REMARK + VALID_REMARK_AURORA;
    public static final String TAGS_DESC_AURORA = " " + PREFIX_TAG + VALID_TAG_AURORA;
    public static final String TAGS_DESC_BOXING = " " + PREFIX_TAG + VALID_TAG_BOXING;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "F"; // "F" not allowed in gender
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "  "; // cannot be spaces only
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2005 12 12"; // wrong date format

    public static final String INVALID_MEMBER_INDEX_DESC = " " + PREFIX_MEMBER + "-2"; // cannot be negative
    public static final String INVALID_EVENT_INDEX_DESC = " " + PREFIX_EVENT + "-2"; // cannot be negative

    public static final String INVALID_HOURS_DESC = " " + PREFIX_HOURS + "twenty"; // cannot be alphabets
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + " "; // cannot be blank

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final Index VALID_INDEX_ONE = Index.fromOneBased(1);
    public static final Index VALID_INDEX_TWO = Index.fromOneBased(2);
    public static final Hours VALID_HOURS_A = ALICE_AURORA.getHours();
    public static final Hours VALID_HOURS_B = BENSON_BOXING.getHours();
    public static final Remark VALID_REMARK_A = ALICE_AURORA.getRemark();
    public static final Remark VALID_REMARK_B = BENSON_BOXING.getRemark();

    public static final EditMemberCommand.EditMemberDescriptor DESC_AMY;
    public static final EditMemberCommand.EditMemberDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditMemberDescriptorBuilder().withName(VALID_NAME_AMY).withGender(VALID_GENDER_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).withGender(VALID_GENDER_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final EditEventCommand.EditEventDescriptor DESC_AURORA;
    public static final EditEventCommand.EditEventDescriptor DESC_BOXING;

    static {
        DESC_AURORA = new EditEventDescriptorBuilder().withName(VALID_NAME_AURORA).withLocation(VALID_LOCATION_AURORA)
                .withDate(VALID_DATE_AURORA)
                .withTags(VALID_TAG_AURORA).build();
        DESC_BOXING = new EditEventDescriptorBuilder().withName(VALID_NAME_BOXING).withLocation(VALID_LOCATION_BOXING)
                .withDate(VALID_DATE_BOXING)
                .withTags(VALID_TAG_BOXING).build();
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the CcaCommander, filtered member list, filtered event list, selected member and selected event
     *   in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CcaCommander expectedCcaCommander = new CcaCommander(actualModel.getCcaCommander());
        List<Member> expectedFilteredMemberList = new ArrayList<>(actualModel.getFilteredMemberList());
        List<Event> expectedFilteredEventList = new ArrayList<>(actualModel.getFilteredEventList());
        List<Enrolment> expectedFilteredEnrolmentList = new ArrayList<>(actualModel.getFilteredEnrolmentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCcaCommander, actualModel.getCcaCommander());
        assertEquals(expectedFilteredMemberList, actualModel.getFilteredMemberList());
        assertEquals(expectedFilteredEventList, actualModel.getFilteredEventList());
        assertEquals(expectedFilteredEnrolmentList, actualModel.getFilteredEnrolmentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the member at the given {@code targetIndex} in the
     * {@code model}'s CcaCommander.
     */
    public static void showMemberAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemberList().size());

        Member member = model.getFilteredMemberList().get(targetIndex.getZeroBased());
        final String[] splitName = member.getName().name.split("\\s+");
        model.updateFilteredMemberList(new MemberNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMemberList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s CcaCommander.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().name.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }
}
