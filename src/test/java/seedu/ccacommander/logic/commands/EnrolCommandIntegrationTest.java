package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;
import static seedu.ccacommander.testutil.TypicalEnrolments.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalEnrolments.BENSON_BOXING;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.testutil.EnrolmentBuilder;


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
    public void execute_newEnrolment_success() {
        Enrolment validEnrolment = new EnrolmentBuilder().withMemberName(ALICE_AURORA.getMemberName().name)
                .withEventName(BENSON_BOXING.getEventName().name)
                .withHours(ALICE_AURORA.getHours().value.toString())
                .withRemark(ALICE_AURORA.getRemark().value)
                .build();

        String commitMessage = String.format(EnrolCommand.MESSAGE_COMMIT,
                validEnrolment.getMemberAndEventEnrolment());
        Model expectedModel = new ModelManager(model.getCcaCommander(), new UserPrefs());
        expectedModel.createEnrolment(validEnrolment);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_TWO, Optional.of(VALID_HOURS_A),
                        Optional.of(VALID_REMARK_A)),
                model,
                String.format(EnrolCommand.MESSAGE_SUCCESS, Messages.format(validEnrolment)),
                expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        assertCommandFailure(new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, Optional.of(VALID_HOURS_A),
                        Optional.of(VALID_REMARK_A)),
                model,
                EnrolCommand.MESSAGE_DUPLICATE_ENROLMENT);
    }

}
