package seedu.ccacommander.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.UniqueEnrolmentList;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.UniqueEventList;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.UniqueMemberList;

/**
 * Wraps all data at the CcaCommander level
 * Duplicates are not allowed (by .isSameMember comparison)
 */
public class CcaCommander implements ReadOnlyCcaCommander {

    private final UniqueMemberList members;
    private final UniqueEventList events;
    private final UniqueEnrolmentList enrolments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        members = new UniqueMemberList();
        events = new UniqueEventList();
        enrolments = new UniqueEnrolmentList();
    }

    public CcaCommander() {}

    /**
     * Creates an CcaCommander using the Members in the {@code toBeCopied}
     */
    public CcaCommander(ReadOnlyCcaCommander toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        this.members.setMembers(members);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Replaces the contents of the enrolment list with {@code enrolments}.
     * {@code enrolments} must not contain duplicate enrolments.
     */
    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments.setEnrolments(enrolments);
    }

    /**
     * Resets the existing data of this {@code CcaCommander} with {@code newData}.
     */
    public void resetData(ReadOnlyCcaCommander newData) {
        requireNonNull(newData);

        setMembers(newData.getMemberList());
        setEvents(newData.getEventList());
        setEnrolments(newData.getEnrolmentList());

    }

    // member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in CcaCommander.
     */
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Adds a member to CcaCommander.
     * The member must not already exist in CcaCommander.
     */
    public void createMember(Member m) {
        members.add(m);
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in CcaCommander.
     * The member identity of {@code editedMember} must not be the same as another existing member in CcaCommander.
     */
    public void setMember(Member target, Member editedMember) {
        requireNonNull(editedMember);

        members.setMember(target, editedMember);
    }

    /**
     * Removes {@code key} from this {@code CcaCommander}.
     * {@code key} must exist in CcaCommander.
     */
    public void removeMember(Member key) {
        members.remove(key);
    }

    // event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in CcaCommander.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to CcaCommander.
     * The event must not already exist in CcaCommander.
     */
    public void createEvent(Event e) {
        events.createEvent(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in CcaCommander.
     * The event identity of {@code editedEvent} must not be the same as another existing event in CcaCommander.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code CcaCommander}.
     * {@code key} must exist in CcaCommander.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }
    // enrolment-level operations

    /**
     * Returns true if an enrolment with the same identity as {@code enrolment} exists in CcaCommander.
     */
    public boolean hasEnrolment(Enrolment enrolment) {
        requireNonNull(enrolment);
        return enrolments.contains(enrolment);
    }

    /**
     * Adds an enrolment to CcaCommander.
     * The enrolment must not already exist in CcaCommander.
     */
    public void createEnrolment(Enrolment a) {
        enrolments.createEnrolment(a);
    }

    /**
     * Removes {@code key} from this {@code CcaCommander}.
     * {@code key} must exist in CcaCommander.
     */
    public void removeEnrolment(Enrolment key) {
        enrolments.remove(key);
    }

    // util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("members", members)
                .add("events", events)
                .add("enrolments", enrolments)
                .toString();
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }
    public ObservableList<Enrolment> getEnrolmentList() {
        return enrolments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CcaCommander)) {
            return false;
        }

        CcaCommander otherCcaCommander = (CcaCommander) other;
        return members.equals(otherCcaCommander.members);
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
