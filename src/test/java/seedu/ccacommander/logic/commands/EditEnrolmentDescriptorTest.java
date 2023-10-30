package seedu.ccacommander.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_AMY_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.DESC_BOB_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_NAME_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_BOXING;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.commands.EditEnrolmentCommand.EditEnrolmentDescriptor;
import seedu.ccacommander.testutil.EditEnrolmentDescriptorBuilder;

public class EditEnrolmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEnrolmentDescriptor descriptorWithSameValues = new EditEnrolmentDescriptor(DESC_AMY_AURORA);
        assertTrue(DESC_AMY_AURORA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY_AURORA.equals(DESC_AMY_AURORA));

        // null -> returns false
        assertFalse(DESC_AMY_AURORA.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY_AURORA.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY_AURORA.equals(DESC_BOB_BOXING));

        // different memberName -> returns false
        EditEnrolmentDescriptor editedAmyAurora = new EditEnrolmentDescriptorBuilder(DESC_AMY_AURORA)
                .withMemberName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY_AURORA.equals(editedAmyAurora));

        // different eventName -> returns false
        editedAmyAurora = new EditEnrolmentDescriptorBuilder(DESC_AMY_AURORA).withEventName(VALID_NAME_BOXING).build();
        assertFalse(DESC_AMY_AURORA.equals(editedAmyAurora));

        // different hours -> returns false
        editedAmyAurora = new EditEnrolmentDescriptorBuilder(DESC_AMY_AURORA).withHours(VALID_HOURS_BOXING).build();
        assertFalse(DESC_AMY_AURORA.equals(editedAmyAurora));


        // different remark -> returns false
        editedAmyAurora = new EditEnrolmentDescriptorBuilder(DESC_AMY_AURORA).withRemark(VALID_REMARK_BOXING).build();
        assertFalse(DESC_AMY_AURORA.equals(editedAmyAurora));
    }

    @Test
    public void toStringMethod() {
        EditEnrolmentDescriptor editEnrolmentDescriptor = new EditEnrolmentDescriptor();
        String expected = EditEnrolmentDescriptor.class.getCanonicalName() + "{memberName="
                + editEnrolmentDescriptor.getMemberName().orElse(null) + ", eventName="
                + editEnrolmentDescriptor.getEventName().orElse(null) + ", hours="
                + editEnrolmentDescriptor.getHours().orElse(null) + ", remark="
                + editEnrolmentDescriptor.getRemark().orElse(null) + "}";
        assertEquals(expected, editEnrolmentDescriptor.toString());
    }
}
