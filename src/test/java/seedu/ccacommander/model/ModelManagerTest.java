package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalEvents.AURORA_BOREALIS;
import static seedu.ccacommander.testutil.TypicalEvents.BOXING_DAY;
import static seedu.ccacommander.testutil.TypicalMembers.ALICE;
import static seedu.ccacommander.testutil.TypicalMembers.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.commons.core.GuiSettings;
import seedu.ccacommander.model.event.EventNameContainsKeywordsPredicate;
import seedu.ccacommander.model.exceptions.RedoStateException;
import seedu.ccacommander.model.exceptions.UndoStateException;
import seedu.ccacommander.model.member.MemberNameContainsKeywordsPredicate;
import seedu.ccacommander.testutil.CcaCommanderBuilder;

public class ModelManagerTest {

    private static final String COMMIT_MESSAGE = "Example Commit";
    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CcaCommander(), new CcaCommander(modelManager.getCcaCommander()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCcaCommanderFilePath(Paths.get("ccacommander/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCcaCommanderFilePath(Paths.get("new/ccacommander/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCcaCommanderFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCcaCommanderFilePath(null));
    }

    @Test
    public void setCcaCommanderFilePath_validPath_setsCcaCommanderFilePath() {
        Path path = Paths.get("ccacommander/book/file/path");
        modelManager.setCcaCommanderFilePath(path);
        assertEquals(path, modelManager.getCcaCommanderFilePath());
    }

    @Test
    public void canUndo_hasPreviousVersion_returnsTrue() {
        modelManager.commit(COMMIT_MESSAGE);
        assertTrue(modelManager.canUndo());
    }

    @Test
    public void canUndo_noPreviousVersion_returnsFalse() {
        assertFalse(modelManager.canUndo());
    }

    @Test
    public void undo_hasPreviousVersion_returnsCommitMessage() {
        modelManager.commit(COMMIT_MESSAGE);
        assertEquals(COMMIT_MESSAGE, modelManager.undo());
    }

    @Test
    public void undo_noPreviousVersion_throwsUndoStateException() {
        assertThrows(UndoStateException.class, () -> modelManager.undo());
    }

    @Test
    public void canRedo_hasNextVersion_returnsTrue() {
        modelManager.commit(COMMIT_MESSAGE);
        modelManager.undo();
        assertTrue(modelManager.canRedo());
    }

    @Test
    public void canRedo_noNextVersion_returnsFalse() {
        assertFalse(modelManager.canRedo());
    }

    @Test
    public void redo_hasNextVersion_returnsCommitMessage() {
        modelManager.commit(COMMIT_MESSAGE);
        modelManager.undo();
        assertEquals(COMMIT_MESSAGE, modelManager.redo());
    }

    @Test
    public void redo_noNextVersion_throwsRedoStateException() {
        assertThrows(RedoStateException.class, () -> modelManager.redo());
    }


    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasMember(null));
    }

    @Test
    public void hasMember_memberNotInCcaCommander_returnsFalse() {
        assertFalse(modelManager.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberInCcaCommander_returnsTrue() {
        modelManager.createMember(ALICE);
        assertTrue(modelManager.hasMember(ALICE));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInCcaCommander_returnsFalse() {
        assertFalse(modelManager.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void hasEvent_eventInCcaCommander_returnsTrue() {
        modelManager.createEvent(AURORA_BOREALIS);
        assertTrue(modelManager.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void getFilteredMemberList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMemberList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }
    @Test
    public void deleteEvents() {
        modelManager.createEvent(AURORA_BOREALIS);
        modelManager.createEvent(BOXING_DAY);

        modelManager.deleteEvent(AURORA_BOREALIS);

        assertTrue(modelManager.hasEvent(BOXING_DAY));
        assertFalse(modelManager.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void updateEvents() {
        modelManager.createEvent(AURORA_BOREALIS);

        modelManager.setEvent(AURORA_BOREALIS, BOXING_DAY);

        assertTrue(modelManager.hasEvent(BOXING_DAY));
        assertFalse(modelManager.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void equals() {
        CcaCommander ccaCommander = new CcaCommanderBuilder().withMember(ALICE).withMember(BENSON)
                .withEvent(AURORA_BOREALIS).withEvent(BOXING_DAY).build();
        CcaCommander differentCcaCommander = new CcaCommander();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(ccaCommander, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(ccaCommander, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different CcaCommander -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCcaCommander, userPrefs)));

        // different filteredList -> returns false
        String[] memberKeywords = ALICE.getName().name.split("\\s+");
        String[] eventKeywords = AURORA_BOREALIS.getName().name.split("\\s+");
        modelManager.updateFilteredMemberList(new MemberNameContainsKeywordsPredicate(Arrays.asList(memberKeywords)));
        modelManager.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(eventKeywords)));
        assertFalse(modelManager.equals(new ModelManager(ccaCommander, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCcaCommanderFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(ccaCommander, differentUserPrefs)));
    }
}
