package seedu.ccacommander.model.member;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ccacommander.commons.util.StringUtil.capitaliseWordsInString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.model.enrolment.Hours;
import seedu.ccacommander.model.enrolment.Remark;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.model.tag.Tag;

/**
 * Represents a Member in CcaCommander.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member {

    // Identity fields
    private final Name name;
    private final Gender gender;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Address> address;
    private final Set<Tag> tags = new HashSet<>();
    private Hours hours;
    private Remark remark;

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Gender gender, Optional<Phone> phone,
                  Optional<Email> email, Optional<Address> address, Set<Tag> tags) {
        requireAllNonNull(name, gender);
        Name capitalisedName = new Name(capitaliseWordsInString(name.name));
        this.name = capitalisedName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.hours = new Hours("0");
        this.remark = new Remark("None");
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Phone getPhone() {
        return phone.orElse(Phone.EMPTY_PHONE);
    }

    public Email getEmail() {
        return email.orElse(Email.EMPTY_EMAIL);
    }

    public Address getAddress() {
        return address.orElse(Address.EMPTY_ADRESS);
    }

    public Hours getHours() {
        return hours;
    }
    public Remark getRemark() {
        return remark;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    public void setHours(Hours hours) {
        this.hours = hours;
    }
    public void setRemark(Remark remark) {
        this.remark = remark;
    }
    /**
     * Returns true if both members have the same name.
     * This defines a weaker notion of equality between two members.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        return otherMember != null
                && otherMember.getName().equals(getName())
                && otherMember.getGender().equals(getGender());
    }

    /**
     * Returns true if both members have the same identity and data fields.
     * This defines a stronger notion of equality between two members.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return name.equals(otherMember.name)
                && gender.equals(otherMember.gender)
                && phone.equals(otherMember.phone)
                && email.equals(otherMember.email)
                && address.equals(otherMember.address)
                && tags.equals(otherMember.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("gender", gender)
                .add("phone", phone.orElse(Phone.EMPTY_PHONE))
                .add("email", email.orElse(Email.EMPTY_EMAIL))
                .add("address", address.orElse(Address.EMPTY_ADRESS))
                .add("tags", tags)
                .toString();
    }

}
