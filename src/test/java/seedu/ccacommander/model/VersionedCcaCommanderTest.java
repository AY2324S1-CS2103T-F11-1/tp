package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.exceptions.RedoStateException;
import seedu.ccacommander.model.exceptions.UndoStateException;

public class VersionedCcaCommanderTest {
    @Test
    public void constructor_validInitialState_success() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());

        assertEquals(1, versionedCcaCommander.viewVersionCaptures().getVersionCaptures().size());
        assertEquals(VersionedCcaCommander.MESSAGE_FIRST_COMMIT,
                versionedCcaCommander.viewVersionCaptures().getVersionCaptures().get(0));
    }

    @Test
    public void commit_validCommitMessage_success() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());

        String commitMessage = "Added new command.";
        versionedCcaCommander.commit(commitMessage);

        assertEquals(2, versionedCcaCommander.viewVersionCaptures().getVersionCaptures().size());
        assertEquals(commitMessage, versionedCcaCommander.viewVersionCaptures().getVersionCaptures().get(1));
    }

    @Test
    public void undo_validUndo_success() throws UndoStateException {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        versionedCcaCommander.commit("First commit");

        String undoneMessage = versionedCcaCommander.undo();

        assertEquals("First commit", undoneMessage);
    }

    @Test
    public void undo_noCommandsToUndo_exceptionThrown() throws UndoStateException {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        assertThrows(UndoStateException.class, () -> versionedCcaCommander.undo());
    }

    @Test
    public void canUndo_hasCommandsToUndo_returnsTrue() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        versionedCcaCommander.commit("First commit");

        assertTrue(versionedCcaCommander.canUndo());
    }

    @Test
    public void canUndo_noCommandsToUndo_returnsFalse() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());

        assertFalse(versionedCcaCommander.canUndo());
    }

    @Test
    public void redo_validRedo_success() throws RedoStateException {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        versionedCcaCommander.commit("First commit");
        versionedCcaCommander.undo();

        String redoneMessage = versionedCcaCommander.redo();

        assertEquals(1, versionedCcaCommander.viewVersionCaptures().getCurrentPointer());
        assertEquals("First commit", redoneMessage);
    }

    @Test
    public void redo_noVersionsToRedo_exceptionThrown() throws RedoStateException {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        assertThrows(RedoStateException.class, () -> versionedCcaCommander.redo());
    }

    @Test
    public void canRedo_hasVersionsToRedo_returnsTrue() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());
        versionedCcaCommander.commit("First commit");
        versionedCcaCommander.undo();

        assertTrue(versionedCcaCommander.canRedo());
    }

    @Test
    public void canRedo_noVersionsToRedo_returnsFalse() {
        VersionedCcaCommander versionedCcaCommander = new VersionedCcaCommander(new CcaCommander());

        assertFalse(versionedCcaCommander.canRedo());
    }
}
