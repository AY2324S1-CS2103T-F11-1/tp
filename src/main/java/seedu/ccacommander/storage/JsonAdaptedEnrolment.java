package seedu.ccacommander.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ccacommander.commons.exceptions.IllegalValueException;
import seedu.ccacommander.model.enrolment.Enrolment;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.shared.Name;

/**
 * Jackson-friendly version of {@link Enrolment}.
 */
class JsonAdaptedEnrolment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Enrolment's %s field is missing!";
    private final String memberName;
    private final String eventName;
    private final String hours;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedEnrolment} with the given enrolment details.
     */
    @JsonCreator
    public JsonAdaptedEnrolment(@JsonProperty("memberName") String memberName,
                                @JsonProperty("eventName") String eventName,
                                @JsonProperty("hours") String hours, @JsonProperty("remark") String remark) {
        this.memberName = memberName;
        this.eventName = eventName;
        this.hours = hours;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Enrolment} into this class for Jackson use.
     */
    public JsonAdaptedEnrolment(Enrolment source) {
        memberName = source.getMemberName().name;
        eventName = source.getEventName().name;
        hours = source.getHours().value.toString();
        remark = source.getRemark().value;
    }

    /**
     * Converts this Jackson-friendly adapted enrolment object into the model's {@code Enrolment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Enrolment toModelType() throws IllegalValueException {
        if (memberName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "memberName"));
        }
        if (!Name.isValidName(memberName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelMemberName = new Name(memberName);

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "eventName"));
        }
        if (!Name.isValidName(eventName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelEventName = new Name(eventName);

        if (hours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Hours.class.getSimpleName()));
        }
        if (!Hours.isValidHours(hours)) {
            throw new IllegalValueException(Hours.MESSAGE_CONSTRAINTS);
        }
        final Hours modelHours = new Hours(hours);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        return new Enrolment(modelMemberName, modelEventName, modelHours, modelRemark);
    }

}
