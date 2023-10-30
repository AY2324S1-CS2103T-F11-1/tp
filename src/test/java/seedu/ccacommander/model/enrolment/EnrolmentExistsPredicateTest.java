package seedu.ccacommander.model.enrolment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.testutil.TypicalEnrolments.ALICE_AURORA;
import static seedu.ccacommander.testutil.TypicalEnrolments.BENSON_BOXING;

import org.junit.jupiter.api.Test;

public class EnrolmentExistsPredicateTest {
    @Test
    public void equals() {

        EnrolmentExistsPredicate aliceAuroraExistsPredicate = new EnrolmentExistsPredicate(ALICE_AURORA);
        EnrolmentExistsPredicate bensonBoxingExistsPredicate = new EnrolmentExistsPredicate(BENSON_BOXING);


        // same values -> returns true
        EnrolmentExistsPredicate aliceAuroraExistsPredicateCopy = new EnrolmentExistsPredicate(ALICE_AURORA);
        assertTrue(aliceAuroraExistsPredicate.equals(aliceAuroraExistsPredicateCopy));

        // same object -> returns true
        assertTrue(aliceAuroraExistsPredicate.equals(aliceAuroraExistsPredicate));

        // null -> returns false
        assertFalse(aliceAuroraExistsPredicate.equals(null));

        // different type -> returns false
        assertFalse(aliceAuroraExistsPredicate.equals(5));

        // different enrolment -> returns false
        assertFalse(aliceAuroraExistsPredicate.equals(bensonBoxingExistsPredicate));
    }

    @Test
    public void toStringMethod() {
        EnrolmentExistsPredicate aliceAuroraExistsPredicate = new EnrolmentExistsPredicate(ALICE_AURORA);
        String expected = EnrolmentExistsPredicate.class.getCanonicalName() + "{enrolmentToCheck="
                + ALICE_AURORA.toString() + "}";
        assertEquals(expected, aliceAuroraExistsPredicate.toString());
    }
}
