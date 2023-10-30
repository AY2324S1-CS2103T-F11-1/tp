package seedu.ccacommander.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ccacommander.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.ccacommander.commons.core.index.Index;
import seedu.ccacommander.commons.util.CollectionUtil;
import seedu.ccacommander.commons.util.ToStringBuilder;
import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.member.Address;
import seedu.ccacommander.model.member.Email;
import seedu.ccacommander.model.member.Gender;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.Phone;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.model.tag.Tag;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;

/**
 * Edits the details of an existing member in CcaCommander.
 */
public class EditMemberCommand extends Command {

    public static final String COMMAND_WORD = "editMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the member identified "
            + "by the index number used in the displayed member list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_MEMBER_SUCCESS = "Edited Member: %1$s";
    public static final String MESSAGE_COMMIT = "Edited Member: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in CCACommander.";

    private final Index index;
    private final EditMemberDescriptor editMemberDescriptor;

    /**
     * @param index of the member in the filtered member list to edit
     * @param editMemberDescriptor details to edit the member with
     */
    public EditMemberCommand(Index index, EditMemberDescriptor editMemberDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemberDescriptor);

        this.index = index;
        this.editMemberDescriptor = new EditMemberDescriptor(editMemberDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        Member memberToEdit = lastShownList.get(index.getZeroBased());
        Member editedMember = createEditedMember(memberToEdit, editMemberDescriptor);

        if (!memberToEdit.isSameMember(editedMember) && model.hasMember(editedMember)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        model.setMember(memberToEdit, editedMember);
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commit(String.format(MESSAGE_COMMIT, editedMember.getName()));

        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(false);
        return new CommandResult(String.format(MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember)));
    }

    /**
     * Creates and returns a {@code Member} with the details of {@code memberToEdit}
     * edited with {@code editMemberDescriptor}.
     */
    private static Member createEditedMember(Member memberToEdit, EditMemberDescriptor editMemberDescriptor) {
        assert memberToEdit != null;

        Name updatedName = editMemberDescriptor.getName().orElse(memberToEdit.getName());
        Gender updatedGender = editMemberDescriptor.getGender().orElse(memberToEdit.getGender());
        Phone updatedPhone = editMemberDescriptor.getPhone().orElse(memberToEdit.getPhone());
        Email updatedEmail = editMemberDescriptor.getEmail().orElse(memberToEdit.getEmail());
        Address updatedAddress = editMemberDescriptor.getAddress().orElse(memberToEdit.getAddress());
        Set<Tag> updatedTags = editMemberDescriptor.getTags().orElse(memberToEdit.getTags());

        return new Member(updatedName, updatedGender, Optional.of(updatedPhone),
                Optional.of(updatedEmail), Optional.of(updatedAddress), updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMemberCommand)) {
            return false;
        }

        EditMemberCommand otherEditMemberCommand = (EditMemberCommand) other;
        return index.equals(otherEditMemberCommand.index)
                && editMemberDescriptor.equals(otherEditMemberCommand.editMemberDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editMemberDescriptor", editMemberDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the member with. Each non-empty field value will replace the
     * corresponding field value of the member.
     */
    public static class EditMemberDescriptor {
        private Name name;
        private Gender gender;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemberDescriptor(EditMemberDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMemberDescriptor)) {
                return false;
            }

            EditMemberDescriptor otherEditMemberDescriptor = (EditMemberDescriptor) other;
            return Objects.equals(name, otherEditMemberDescriptor.name)
                    && Objects.equals(gender, otherEditMemberDescriptor.gender)
                    && Objects.equals(phone, otherEditMemberDescriptor.phone)
                    && Objects.equals(email, otherEditMemberDescriptor.email)
                    && Objects.equals(address, otherEditMemberDescriptor.address)
                    && Objects.equals(tags, otherEditMemberDescriptor.tags);
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
}
