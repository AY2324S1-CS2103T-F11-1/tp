package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateEventCommand}.
 */
public class CreateEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().build();

        String commitMessage = String.format(CreateEventCommand.MESSAGE_COMMIT, validEvent.getName());
        Model expectedModel = new ModelManager(model.getCcaCommander(), new UserPrefs());
        expectedModel.createEvent(validEvent);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(new CreateEventCommand(validEvent), model,
                String.format(CreateEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                expectedModel);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event memberInList = model.getCcaCommander().getEventList().get(0);
        assertCommandFailure(new CreateEventCommand(memberInList), model,
                CreateEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

}
