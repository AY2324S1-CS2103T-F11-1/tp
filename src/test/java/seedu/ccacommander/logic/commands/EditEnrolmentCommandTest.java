package seedu.ccacommander.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.EditEnrolmentCommand.EditEnrolmentDescriptor;
import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.testutil.AttendanceBuilder;
import seedu.ccacommander.testutil.EditEnrolmentDescriptorBuilder;
import seedu.ccacommander.testutil.EventBuilder;
import seedu.ccacommander.testutil.MemberBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_AMY_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_BOB_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_LOCATION_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_NAME_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_TAG_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.logic.commands.CommandTestUtil.showEnrolmentAtIndex;
import static seedu.ccacommander.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.ccacommander.logic.commands.CommandTestUtil.showMemberAtIndex;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_FIRST_ENROLMENT;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_SECOND_ENROLMENT;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.ccacommander.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEnrolmentCommand.
 */
public class EditEnrolmentCommandTest {

    private Model model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Attendance editedEnrolment =
                new AttendanceBuilder().withHours(VALID_HOURS_BOXING).withRemark(VALID_REMARK_BOXING).build();
        EditEnrolmentDescriptor descriptor = new EditEnrolmentDescriptorBuilder(editedEnrolment).build();
        EditEnrolmentCommand editEnrolmentCommand =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_FIRST_EVENT, descriptor);

        String commitMessage =
                String.format(EditEnrolmentCommand.MESSAGE_COMMIT, editedEnrolment.getMemberAndEventAttendance());
        String expectedMessage = String.format(EditEnrolmentCommand.MESSAGE_EDIT_ENROLMENT_SUCCESS,
                Messages.format(editedEnrolment));

        Model expectedModel = new ModelManager(new CcaCommander(model.getCcaCommander()), new UserPrefs());
        expectedModel.setEnrolment(model.getFilteredAttendanceList().get(0), editedEnrolment);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(editEnrolmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMember = Index.fromOneBased(model.getFilteredMemberList().size());
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Index indexLastEnrolment = Index.fromOneBased(model.getFilteredAttendanceList().size());

        Attendance lastEnrolment = model.getFilteredAttendanceList().get(indexLastEnrolment.getZeroBased());

        AttendanceBuilder enrolmentInList = new AttendanceBuilder(lastEnrolment);
        Attendance editedEnrolment = enrolmentInList.withHours(VALID_HOURS_BOXING).build();

        EditEnrolmentDescriptor descriptor = new EditEnrolmentDescriptorBuilder(editedEnrolment).build();

        EditEnrolmentCommand editEnrolmentCommand =
                new EditEnrolmentCommand(indexLastMember, indexLastEvent, descriptor);

        String commitMessage =
                String.format(EditEnrolmentCommand.MESSAGE_COMMIT, editedEnrolment.getMemberAndEventAttendance());
        String expectedMessage = String.format(EditEnrolmentCommand.MESSAGE_EDIT_ENROLMENT_SUCCESS,
                Messages.format(editedEnrolment));

        Model expectedModel = new ModelManager(new CcaCommander(model.getCcaCommander()), new UserPrefs());
        expectedModel.setEnrolment(lastEnrolment, editedEnrolment);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(editEnrolmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEnrolmentAtIndex(model, INDEX_FIRST_ENROLMENT);

        Attendance enrolmentInFilteredList = model.getFilteredAttendanceList().get(INDEX_FIRST_ENROLMENT.getZeroBased());
        Attendance editedEnrolment =
                new AttendanceBuilder(enrolmentInFilteredList).withHours(VALID_HOURS_BOXING).build();
        EditEnrolmentCommand editEnrolmentCommand = new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_FIRST_EVENT,
                new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_BOXING).build());

        String commitMessage =
                String.format(EditEnrolmentCommand.MESSAGE_COMMIT, editedEnrolment.getMemberAndEventAttendance());
        String expectedMessage = String.format(EditEnrolmentCommand.MESSAGE_EDIT_ENROLMENT_SUCCESS,
                Messages.format(editedEnrolment));

        Model expectedModel = new ModelManager(new CcaCommander(model.getCcaCommander()), new UserPrefs());
        expectedModel.setEnrolment(model.getFilteredAttendanceList().get(0), editedEnrolment);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(editEnrolmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        EditEnrolmentDescriptor descriptor = new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_BOXING).build();
        EditEnrolmentCommand editEventCommand =
                new EditEnrolmentCommand(outOfBoundIndex, INDEX_SECOND_ENROLMENT, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidMemberIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEnrolmentDescriptor descriptor = new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_BOXING).build();
        EditEnrolmentCommand editEventCommand =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where member index is larger than size of filtered member list,
     * but smaller than size of CcaCommander
     */
    @Test
    public void execute_invalidMemberIndexFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        Index outOfBoundIndex = INDEX_SECOND_MEMBER;
        // ensures that outOfBoundIndex is still in bounds of CcaCommander list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCcaCommander().getMemberList().size());

        EditEnrolmentCommand editEventCommand = new EditEnrolmentCommand(outOfBoundIndex, INDEX_SECOND_EVENT,
                new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_BOXING).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where event index is larger than size of filtered event list,
     * but smaller than size of CcaCommander
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of CcaCommander list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCcaCommander().getEventList().size());

        EditEnrolmentCommand editEventCommand = new EditEnrolmentCommand(INDEX_FIRST_MEMBER, outOfBoundIndex,
                new EditEnrolmentDescriptorBuilder().withHours(VALID_HOURS_BOXING).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEnrolmentCommand standardCommand =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_FIRST_EVENT, DESC_AMY_AURORA);

        // same values -> returns true
        EditEnrolmentDescriptor copyDescriptor = new EditEnrolmentDescriptor(DESC_AMY_AURORA);
        EditEnrolmentCommand commandWithSameValues =
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different member index -> returns false
        assertFalse(standardCommand.equals(
                new EditEnrolmentCommand(INDEX_SECOND_MEMBER, INDEX_FIRST_EVENT, DESC_AMY_AURORA)));

        // different event index -> returns false
        assertFalse(standardCommand.equals(
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_SECOND_EVENT, DESC_AMY_AURORA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditEnrolmentCommand(INDEX_FIRST_MEMBER, INDEX_FIRST_EVENT, DESC_BOB_BOXING)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEnrolmentDescriptor editEnrolmentDescriptor = new EditEnrolmentDescriptor();
        EditEnrolmentCommand editEventCommand = new EditEnrolmentCommand(index, index, editEnrolmentDescriptor);
        String expected = EditEnrolmentCommand.class.getCanonicalName() + "{memberIndex=" + index + ", eventIndex="
                + index + ", editEnrolmentDescriptor=" + editEnrolmentDescriptor + "}";
        assertEquals(expected, editEventCommand.toString());
    }

}
