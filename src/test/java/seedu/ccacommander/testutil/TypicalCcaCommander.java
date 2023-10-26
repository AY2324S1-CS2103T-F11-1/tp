package seedu.ccacommander.testutil;

import static seedu.ccacommander.testutil.TypicalAttendances.getTypicalAttendance;
import static seedu.ccacommander.testutil.TypicalEvents.getTypicalEvents;
import static seedu.ccacommander.testutil.TypicalMembers.getTypicalMembers;

import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;

/**
 * A utility class which returns an CcaCommander with all typical members and events.
 */
public class TypicalCcaCommander {
    /**
     * Returns a {@code CcaCommander} with only typical members.
     */
    public static CcaCommander getTypicalMemberCcaCommander() {
        CcaCommander cc = new CcaCommander();
        for (Member member : getTypicalMembers()) {
            cc.createMember(member);
        }

        return cc;
    }

    /**
     * Returns a {@code CcaCommander} with only typical events.
     */
    public static CcaCommander getTypicalEventCcaCommander() {
        CcaCommander cc = new CcaCommander();
        for (Event event: getTypicalEvents()) {
            cc.createEvent(event);
        }

        return cc;
    }

    /**
     * Returns a {@code CcaCommander} with only typical attendances.
     */
    public static CcaCommander getTypicalAttendanceCcaCommander() {
        CcaCommander cc = new CcaCommander();
        for (Attendance attendance: getTypicalAttendance()) {
            cc.createAttendance(attendance);
        }

        return cc;
    }

    /**
     * Returns an {@code CcaCommander} with all the typical members and events.
     */
    public static CcaCommander getTypicalCcaCommander() {
        CcaCommander cc = new CcaCommander();
        for (Member member : getTypicalMembers()) {
            cc.createMember(member);
        }

        for (Event event: getTypicalEvents()) {
            cc.createEvent(event);
        }

        for (Attendance attendance: getTypicalAttendance()) {
            cc.createAttendance(attendance);
        }

        return cc;
    }
}
