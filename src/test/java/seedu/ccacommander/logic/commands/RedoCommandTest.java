package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;

public class RedoCommandTest {
    public static final String COMMIT_MESSAGE = "Commit Message";
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
    }

    @Test
    public void execute_hasNextState_throwsCommandException() {
        model.commit(COMMIT_MESSAGE);
        model.undo();

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS_REDO, COMMIT_MESSAGE);
        expectedModel.commit(COMMIT_MESSAGE);

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noNextState_throwsCommandException() {
        model.commit(COMMIT_MESSAGE);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_AVAILABLE_COMMAND);
    }
}
