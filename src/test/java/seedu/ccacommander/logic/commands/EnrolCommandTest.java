package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_B;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_ONE;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_INDEX_TWO;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_A;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_REMARK_B;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalEnrolments.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalEvents.AURORA_BOREALIS;
import static seedu.ccacommander.testutil.TypicalMembers.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ccacommander.commons.core.GuiSettings;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ReadOnlyCcaCommander;
import seedu.ccacommander.model.ReadOnlyUserPrefs;
import seedu.ccacommander.model.VersionCaptures;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.UniqueEventList;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.UniqueMemberList;
import seedu.ccacommander.testutil.EnrolmentBuilder;

public class EnrolCommandTest {
    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EnrolCommand(null, null, null, null));
    }

    @Test
    public void execute_enrolmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEnrolmentAdded modelStub = new ModelStubAcceptingEnrolmentAdded();
        Enrolment validEnrolment = new EnrolmentBuilder().build();

        CommandResult commandResult =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A)
                        .execute(modelStub);

        assertEquals(String.format(EnrolCommand.MESSAGE_SUCCESS, Messages.format(validEnrolment)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEnrolment), modelStub.enrolmentsAdded);
    }

    @Test
    public void execute_duplicateEnrolment_throwsCommandException() {
        EnrolCommand enrolCommand =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A);
        ModelStubWithEnrolment modelStub = new ModelStubWithEnrolment(ALICE_AURORA);

        assertThrows(CommandException.class,
                EnrolCommand.MESSAGE_DUPLICATE_ENROLMENT, () -> enrolCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        EnrolCommand enrolEventOneCommand =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A);
        EnrolCommand enrolEventTwoCommand =
                new EnrolCommand(VALID_INDEX_TWO, VALID_INDEX_TWO, VALID_HOURS_B, VALID_REMARK_B);

        // same object -> returns true
        assertTrue(enrolEventOneCommand.equals(enrolEventOneCommand));

        // same values -> returns true
        EnrolCommand enrolEventOneCommandCopy =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A);
        assertTrue(enrolEventOneCommand.equals(enrolEventOneCommandCopy));

        // different types -> returns false
        assertFalse(enrolEventOneCommand.equals(1));

        // null -> returns false
        assertFalse(enrolEventOneCommand.equals(null));

        // different member -> returns false
        assertFalse(enrolEventOneCommand.equals(enrolEventTwoCommand));
    }

    @Test
    public void toStringMethod() {
        EnrolCommand enrolCommand =
                new EnrolCommand(VALID_INDEX_ONE, VALID_INDEX_ONE, VALID_HOURS_A, VALID_REMARK_A);
        String expected = EnrolCommand.class.getCanonicalName() + "{member index=" + VALID_INDEX_ONE
                + ", event index=" + VALID_INDEX_ONE + ", hours=" + VALID_HOURS_A
                + ", remark=" + VALID_REMARK_A + "}";
        assertEquals(enrolCommand.toString(), expected);
    }

    /**
     * A default model stub that has all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCcaCommanderFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCcaCommanderFilePath(Path ccaCommanderFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCcaCommander(ReadOnlyCcaCommander newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCcaCommander getCcaCommander() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member editedMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event event, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createEnrolment(Enrolment enrolment) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasEnrolment(Enrolment enrolment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEnrolment(Enrolment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Enrolment> getFilteredEnrolmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEnrolmentList(Predicate<Enrolment> enrolment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit(String commitMessage) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public VersionCaptures viewVersionCaptures() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single enrolment.
     */
    private class ModelStubWithEnrolment extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();
        final UniqueEventList events = new UniqueEventList();
        private final Enrolment enrolment;

        ModelStubWithEnrolment(Enrolment enrolment) {
            requireNonNull(enrolment);
            events.createEvent(AURORA_BOREALIS);
            members.add(ALICE);
            this.enrolment = enrolment;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return new FilteredList<>(events.asUnmodifiableObservableList());
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return new FilteredList<>(members.asUnmodifiableObservableList());
        }
        @Override
        public boolean hasEnrolment(Enrolment enrolment) {
            requireNonNull(enrolment);
            return this.enrolment.isSameEnrolment(enrolment);
        }
    }

    /**
     * A Model stub that always accept the enrolment being added.
     */
    private class ModelStubAcceptingEnrolmentAdded extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();
        final UniqueEventList events = new UniqueEventList();
        final ArrayList<Enrolment> enrolmentsAdded = new ArrayList<>();
        final ArrayList<String> commitMessages = new ArrayList<>();

        public ModelStubAcceptingEnrolmentAdded() {
            events.createEvent(AURORA_BOREALIS);
            members.add(ALICE);
        }

        @Override
        public void commit(String commitMessage) {
            commitMessages.add(commitMessage);
        }

        @Override
        public boolean hasEnrolment(Enrolment enrolment) {
            requireNonNull(enrolment);
            return enrolmentsAdded.stream().anyMatch(enrolment::isSameEnrolment);
        }

        @Override
        public void createEnrolment(Enrolment enrolment) {
            requireNonNull(enrolment);
            enrolmentsAdded.add(enrolment);
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return new FilteredList<>(events.asUnmodifiableObservableList());
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return new FilteredList<>(members.asUnmodifiableObservableList());
        }

        @Override
        public ReadOnlyCcaCommander getCcaCommander() {
            return new CcaCommander();
        }
    }

}
