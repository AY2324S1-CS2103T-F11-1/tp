package seedu.ccacommander.model.member;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.ccacommander.model.shared.Name;

/**
 * Tests that a {@code Member}'s {@code Name} is in the given collection.
 */
public class MemberInNameCollectionPredicate implements Predicate<Member> {

    private final Collection<Name> memberNames;

    public MemberInNameCollectionPredicate(Collection<Name> memberNames) {
        this.memberNames = memberNames;
    }

    @Override
    public boolean test(Member member) {
        return memberNames.stream().anyMatch(name -> member.getName().equals(name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MemberInNameCollectionPredicate)) {
            return false;
        }

        Set<Name> names = new HashSet<>(memberNames);
        Set<Name> otherNames = new HashSet<>(((MemberInNameCollectionPredicate) other).memberNames);

        return names.equals(otherNames);
    }
}
