package seedu.ccacommander.model.attendance;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.model.shared.Name;

/**
 * Represents an attendance in CcaCommander.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Attendance {

    // Identity fields
    private final Name memberName;
    private final Name eventName;

    // Data fields
    private final Hours hours;
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */
    public Attendance(Name memberName, Name eventName, Hours hours, Remark remark) {
        requireAllNonNull(memberName, eventName, hours, remark);
        this.memberName = memberName;
        this.eventName = eventName;
        this.hours = hours;
        this.remark = remark;
    }

    public Name getMemberName() {
        return memberName;
    }

    public Name getEventName() {
        return eventName;
    }

    public Hours getHours() {
        return hours;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both attendance have the same identity fields.
     * This defines a weaker notion of equality between two attendance.
     */
    public boolean isSameAttendance(Attendance otherAttendance) {
        if (otherAttendance == this) {
            return true;
        }

        return otherAttendance != null
                && otherAttendance.getMemberName().equals(getMemberName())
                && otherAttendance.getEventName().equals(getEventName());
    }

    /**
     * Returns true if both attendance have the same identity and data fields.
     * This defines a stronger notion of equality between two attendances.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return memberName.equals(otherAttendance.memberName)
                && eventName.equals(otherAttendance.eventName)
                && hours.equals(otherAttendance.hours)
                && remark.equals(otherAttendance.remark);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(memberName, eventName, hours, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("member name", memberName)
                .add("event name", eventName)
                .add("hours", hours)
                .add("remark", remark)
                .toString();
    }
}
