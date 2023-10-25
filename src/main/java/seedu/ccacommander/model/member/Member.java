package seedu.ccacommander.model.member;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ccacommander.commons.util.StringUtil.capitaliseWordsInString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.ccacommander.commons.util.ToStringBuilder;
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
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, gender, phone, email, address, tags);
        Name capitalisedName = new Name(capitaliseWordsInString(name.name));
        this.name = capitalisedName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }


    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
                && otherMember.getName().equals(getName());
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
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
