package seedu.ccacommander.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.logic.commands.CommandTestUtil.VALID_HOURS_AURORA;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalAttendances.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalAttendances.BENSON_BOXING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.ccacommander.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.ccacommander.testutil.AttendanceBuilder;

public class UniqueAttendanceListTest {

    private final UniqueAttendanceList uniqueAttendanceList = new UniqueAttendanceList();

    @Test
    public void contains_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.contains(null));
    }

    @Test
    public void contains_attendanceNotInList_returnsFalse() {
        assertFalse(uniqueAttendanceList.contains(ALICE_AURORA));
    }

    @Test
    public void contains_attendanceInList_returnsTrue() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        assertTrue(uniqueAttendanceList.contains(ALICE_AURORA));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        Attendance editedHours = new AttendanceBuilder(ALICE_AURORA).withHours(VALID_HOURS_AURORA)
                .build();
        assertTrue(uniqueAttendanceList.contains(editedHours));
    }

    @Test
    public void add_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.createAttendance(null));
    }

    @Test
    public void add_duplicateAttendance_throwsDuplicateAttendanceException() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        assertThrows(DuplicateAttendanceException.class, () -> uniqueAttendanceList.createAttendance(ALICE_AURORA));
    }

    @Test
    public void setAttendance_nullTargetAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.setAttendance(null, ALICE_AURORA));
    }

    @Test
    public void setAttendance_nullEditedAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.setAttendance(ALICE_AURORA, null));
    }

    @Test
    public void setAttendance_targetAttendanceNotInList_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () ->
                uniqueAttendanceList.setAttendance(ALICE_AURORA, ALICE_AURORA));
    }

    @Test
    public void setAttendance_editedAttendanceIsSameAttendance_success() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        uniqueAttendanceList.setAttendance(ALICE_AURORA, ALICE_AURORA);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.createAttendance(ALICE_AURORA);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setEvent_editedAttendanceHasSameIdentity_success() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        Attendance editedHours = new AttendanceBuilder(ALICE_AURORA).withHours(VALID_HOURS_AURORA)
                .build();
        uniqueAttendanceList.setAttendance(ALICE_AURORA, editedHours);
        UniqueAttendanceList expectedUniqueEventList = new UniqueAttendanceList();
        expectedUniqueEventList.createAttendance(editedHours);
        assertEquals(expectedUniqueEventList, uniqueAttendanceList);
    }

    @Test
    public void setAttendance_editedAttendanceHasDifferentIdentity_success() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        uniqueAttendanceList.setAttendance(ALICE_AURORA, BENSON_BOXING);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.createAttendance(BENSON_BOXING);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setAttendance_editedAttendanceIsNonUnique_throwsDuplicateAttendanceException() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        uniqueAttendanceList.createAttendance(BENSON_BOXING);
        assertThrows(DuplicateAttendanceException.class, () ->
                uniqueAttendanceList.setAttendance(ALICE_AURORA, BENSON_BOXING));
    }

    @Test
    public void remove_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.remove(null));
    }

    @Test
    public void remove_attendanceDoesNotExist_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> uniqueAttendanceList.remove(ALICE_AURORA));
    }

    @Test
    public void remove_existingAttendance_removesAttendance() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        uniqueAttendanceList.remove(ALICE_AURORA);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setAttendances_nullUniqueAttendanceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAttendanceList.setAttendances((UniqueAttendanceList) null));
    }

    @Test
    public void setAttendances_uniqueAttendanceList_replacesOwnListWithProvidedUniqueAttendanceList() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.createAttendance(BENSON_BOXING);
        uniqueAttendanceList.setAttendances(expectedUniqueAttendanceList);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setAttendances_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAttendanceList.setAttendances((List<Attendance>) null));
    }

    @Test
    public void setAttendances_list_replacesOwnListWithProvidedList() {
        uniqueAttendanceList.createAttendance(ALICE_AURORA);
        List<Attendance> attendanceList = Collections.singletonList(BENSON_BOXING);
        uniqueAttendanceList.setAttendances(attendanceList);
        UniqueAttendanceList expectedUniqueAttendanceList = new UniqueAttendanceList();
        expectedUniqueAttendanceList.createAttendance(BENSON_BOXING);
        assertEquals(expectedUniqueAttendanceList, uniqueAttendanceList);
    }

    @Test
    public void setAttendances_listWithDuplicateAttendances_throwsDuplicateAttendanceException() {
        List<Attendance> listWithDuplicateAttendances = Arrays.asList(ALICE_AURORA, ALICE_AURORA);
        assertThrows(DuplicateAttendanceException.class, () ->
                uniqueAttendanceList.setAttendances(listWithDuplicateAttendances));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueAttendanceList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueAttendanceList.asUnmodifiableObservableList().toString(), uniqueAttendanceList.toString());
    }
}
