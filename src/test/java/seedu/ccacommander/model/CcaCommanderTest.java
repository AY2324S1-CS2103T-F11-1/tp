package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_DATE_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_DATE_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_LOCATION_AURORA;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_LOCATION_BOXING;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;
import static seedu.ccacommander.testutil.TypicalEvents.AURORA_BOREALIS;
import static seedu.ccacommander.testutil.TypicalMembers.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.exceptions.DuplicateEventException;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.exceptions.DuplicateMemberException;
import seedu.ccacommander.testutil.EventBuilder;
import seedu.ccacommander.testutil.MemberBuilder;

public class CcaCommanderTest {

    private final CcaCommander ccaCommander = new CcaCommander();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ccaCommander.getMemberList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaCommander.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCcaCommander_replacesData() {
        CcaCommander newData = getTypicalCcaCommander();
        ccaCommander.resetData(newData);
        assertEquals(newData, ccaCommander);
    }

    @Test
    public void resetData_withDuplicateMembers_throwsDuplicateMemberException() {
        // Two members with the same identity fields
        Member editedAlice = new MemberBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Member> newMembers = Arrays.asList(ALICE, editedAlice);
        CcaCommanderStub newData = new CcaCommanderStub(newMembers, Arrays.asList());

        assertThrows(DuplicateMemberException.class, () -> ccaCommander.resetData(newData));
    }

    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaCommander.hasMember(null));
    }

    @Test
    public void hasMember_memberNotInCcaCommander_returnsFalse() {
        assertFalse(ccaCommander.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberInCcaCommander_returnsTrue() {
        ccaCommander.createMember(ALICE);
        assertTrue(ccaCommander.hasMember(ALICE));
    }

    @Test
    public void hasMember_memberWithSameIdentityFieldsInCcaCommander_returnsTrue() {
        ccaCommander.createMember(ALICE);
        Member editedAlice = new MemberBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ccaCommander.hasMember(editedAlice));
    }

    @Test
    public void getMemberList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> ccaCommander.getMemberList().remove(0));
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventException() {
        // Two events with the same identity fields
        Event editedAurora = new EventBuilder(AURORA_BOREALIS).withDate(VALID_DATE_AURORA)
                .withLocation(VALID_LOCATION_AURORA).build();
        List<Event> newEvents = Arrays.asList(AURORA_BOREALIS, editedAurora);
        CcaCommanderStub newData = new CcaCommanderStub(Arrays.asList(), newEvents);

        assertThrows(DuplicateEventException.class, () -> ccaCommander.resetData(newData));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaCommander.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInCcaCommander_returnsFalse() {
        assertFalse(ccaCommander.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void hasEvent_eventInCcaCommander_returnsTrue() {
        ccaCommander.createEvent(AURORA_BOREALIS);
        assertTrue(ccaCommander.hasEvent(AURORA_BOREALIS));
    }

    @Test
    public void hasEvent_eventWithSameNameInCcaCommander_returnsFalse() {
        ccaCommander.createEvent(AURORA_BOREALIS);
        Event editedAurora = new EventBuilder(AURORA_BOREALIS).withDate(VALID_DATE_BOXING)
                .withLocation(VALID_LOCATION_BOXING)
                .build();
        assertFalse(ccaCommander.hasEvent(editedAurora));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> ccaCommander.getEventList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = CcaCommander.class.getCanonicalName() + "{members=" + ccaCommander.getMemberList()
                + ", events=" + ccaCommander.getEventList() + ", attendances=" + ccaCommander.getEventList() + "}";
        assertEquals(expected, ccaCommander.toString());
    }

    /**
     * A stub ReadOnlyCcaCommander whose members list can violate interface constraints.
     */
    private static class CcaCommanderStub implements ReadOnlyCcaCommander {
        private final ObservableList<Member> members = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Attendance> attendances = FXCollections.observableArrayList();

        CcaCommanderStub(Collection<Member> members, Collection<Event> events) {
            this.members.setAll(members);
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }
        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
        @Override
        public ObservableList<Attendance> getAttendanceList() {
            return attendances;
        }
    }

}
