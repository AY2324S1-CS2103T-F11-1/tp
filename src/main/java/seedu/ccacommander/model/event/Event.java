package seedu.ccacommander.model.event;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.model.tag.Tag;

/**
 * Represents an Event in CcaCommander.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // Identity fields
    private final Name name;

    // Data fields
    private final Location location;
    private final EventDate eventDate;

    // Enrolment data fields
    private Hours hours;
    private Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, EventDate eventDate, Location location, Set<Tag> tags) {
        requireAllNonNull(name, eventDate, location);
        this.name = name;
        this.eventDate = eventDate;
        this.location = location;
        this.tags.addAll(tags);
        this.hours = new Hours("0");
        this.remark = new Remark("None");
    }

    public Name getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public EventDate getDate() {
        return this.eventDate;
    }

    public Hours getHours() {
        return this.hours;
    }
    public Remark getRemark() {
        return this.remark;
    }
    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both events have the same name, date and location.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getLocation().equals(getLocation());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return this.name.equals(otherEvent.name)
                && this.location.equals(otherEvent.location)
                && this.eventDate.equals(otherEvent.eventDate)
                && this.tags.equals(otherEvent.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name, this.location, this.eventDate, this.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", eventDate)
                .add("location", location)
                .add("tags", tags)
                .toString();
    }

}
