package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyCcaCommander_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commit(ClearCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCcaCommander_success() {
        Model model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
        expectedModel.setCcaCommander(new CcaCommander());
        expectedModel.commit(ClearCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
