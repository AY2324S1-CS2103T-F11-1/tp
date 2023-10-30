package seedu.ccacommander.model.event;

import java.util.function.Predicate;

/**
 * Tests that an {@code Event}'s identity is the same as the target {@code Event}.
 */
public class SameEventPredicate implements Predicate<Event> {
    private Event target;

    public SameEventPredicate(Event target) {
        this.target = target;
    }

    @Override
    public boolean test(Event event) {
        return event.isSameEvent(target);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SameEventPredicate
                && target.equals(((SameEventPredicate) other)
                .target));
    }
}

