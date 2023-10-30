package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalEvents.BOXING_DAY;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
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
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.testutil.EventBuilder;

public class CreateEventCommandTest {

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateEventCommand(null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new CreateEventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(CreateEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event validEvent = new EventBuilder().build();
        CreateEventCommand createEventCommand = new CreateEventCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        assertThrows(CommandException.class,
                CreateEventCommand.MESSAGE_DUPLICATE_EVENT, () -> createEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Event party = new EventBuilder().withName("Party").build();
        Event marathon = new EventBuilder().withName("Marathon").build();
        CreateEventCommand addPartyCommand = new CreateEventCommand(party);
        CreateEventCommand addMarathonCommand = new CreateEventCommand(marathon);

        // same object -> returns true
        assertTrue(addPartyCommand.equals(addPartyCommand));

        // same values -> returns true
        CreateEventCommand addPartyCommandCopy = new CreateEventCommand(party);
        assertTrue(addPartyCommand.equals(addPartyCommandCopy));

        // different types -> returns false
        assertFalse(addPartyCommand.equals(1));

        // null -> returns false
        assertFalse(addPartyCommand.equals(null));

        // different member -> returns false
        assertFalse(addPartyCommand.equals(addMarathonCommand));
    }

    @Test
    public void toStringMethod() {
        CreateEventCommand createEventCommand = new CreateEventCommand(BOXING_DAY);
        String expected = CreateEventCommand.class.getCanonicalName() + "{toAdd=" + BOXING_DAY + "}";
        assertEquals(expected, createEventCommand.toString());
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
     * A Model stub that contains a single member.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();
        final ArrayList<String> commitMessages = new ArrayList<>();

        @Override
        public void commit(String commitMessage) {
            commitMessages.add(commitMessage);
        }
        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void createEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyCcaCommander getCcaCommander() {
            return new CcaCommander();
        }
    }

}
