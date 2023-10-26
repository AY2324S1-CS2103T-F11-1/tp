package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalAttendances.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalAttendances.BENSON_BOXING;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.testutil.AttendanceBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code EnrolCommand}.
 */
public class EnrolCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
    }

    @Test
    public void execute_newAttendance_success() {
        Attendance validAttendance = new AttendanceBuilder().withMemberName(ALICE_AURORA.getMemberName().name)
                .withEventName(BENSON_BOXING.getEventName().name)
                .withHours(ALICE_AURORA.getHours().value.toString())
                .withRemark(ALICE_AURORA.getRemark().value)
                .build();

        String commitMessage = String.format(EnrolCommand.MESSAGE_COMMIT, validAttendance.toString());
        Model expectedModel = new ModelManager(model.getCcaCommander(), new UserPrefs());
        expectedModel.createAttendance(validAttendance);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_TWO, VALID_HOURS_A, VALID_REMARK_A),
                model, String.format(EnrolCommand.MESSAGE_SUCCESS, Messages.format(validAttendance)),
                expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        assertCommandFailure(new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A),
                model, EnrolCommand.MESSAGE_DUPLICATE_ATTENDANCE);
    }

}
