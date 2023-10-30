package seedu.ccacommander.model.member;

import java.util.function.Predicate;

/**
 * Tests that a {@code Member}'s identity is the same as the target {@code Event}.
 */
public class SameMemberPredicate implements Predicate<Member> {
    private Member target;

    public SameMemberPredicate(Member target) {
        this.target = target;
    }

    @Override
    public boolean test(Member member) {
        return member.isSameMember(target);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SameMemberPredicate
                && target.equals(((SameMemberPredicate) other)
                .target));
    }
}

