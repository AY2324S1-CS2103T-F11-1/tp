package seedu.ccacommander.testutil;

import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.shared.Name;

/**
 * A utility class to help with building Enrolment objects.
 */
public class EnrolmentBuilder {

    public static final String DEFAULT_MEMBER_NAME = "Alice Pauline";
    public static final String DEFAULT_EVENT_NAME = "Aurora Borealis";
    public static final String DEFAULT_HOURS = "3";
    public static final String DEFAULT_REMARK = "Role: Photographer";

    private Name memberName;
    private Name eventName;
    private Hours hours;
    private Remark remark;

    /**
     * Creates an {@code EnrolmentBuilder} with the default details.
     */
    public EnrolmentBuilder() {
        memberName = new Name(DEFAULT_MEMBER_NAME);
        eventName = new Name(DEFAULT_EVENT_NAME);
        hours = new Hours(DEFAULT_HOURS);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the EnrolmentBuilder with the data of {@code enrolmentToCopy}.
     */
    public EnrolmentBuilder(Enrolment enrolmentToCopy) {
        memberName = enrolmentToCopy.getMemberName();
        eventName = enrolmentToCopy.getEventName();
        hours = enrolmentToCopy.getHours();
        remark = enrolmentToCopy.getRemark();
    }

    /**
     * Sets the Member's {@code Name} of the {@code Enrolment} that we are building.
     */
    public EnrolmentBuilder withMemberName(String name) {
        this.memberName = new Name(name);
        return this;
    }

    /**
     * Sets the Event's {@code Name} of the {@code Enrolment} that we are building.
     */
    public EnrolmentBuilder withEventName(String name) {
        this.eventName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Hours} of the {@code Enrolment} that we are building.
     */
    public EnrolmentBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Enrolment} that we are building.
     */
    public EnrolmentBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }
    public Enrolment build() {
        return new Enrolment(memberName, eventName, hours, remark);
    }

}
