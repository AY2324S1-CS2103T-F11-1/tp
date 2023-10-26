package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;

public class UndoCommandTest {
    public static final String COMMIT_MESSAGE = "Commit Message";
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
    }

    @Test
    public void execute_hasPreviousState_success() {
        model.commit(COMMIT_MESSAGE);

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS_UNDO, COMMIT_MESSAGE);
        expectedModel.commit(COMMIT_MESSAGE);
        expectedModel.undo();

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noPreviousState_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_AVAILABLE_COMMAND);
    }
}
