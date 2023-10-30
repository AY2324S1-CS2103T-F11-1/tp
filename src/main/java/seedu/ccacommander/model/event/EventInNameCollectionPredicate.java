package seedu.ccacommander.model.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.ccacommander.model.shared.Name;

/**
 * Tests that a {@code Event}'s {@code Name} is in the given collection.
 */
public class EventInNameCollectionPredicate implements Predicate<Event> {

    private final Collection<Name> eventNames;

    public EventInNameCollectionPredicate(Collection<Name> eventNames) {
        this.eventNames = eventNames;
    }

    @Override
    public boolean test(Event event) {
        return eventNames.stream().anyMatch(name -> event.getName().equals(name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventInNameCollectionPredicate)) {
            return false;
        }

        Set<Name> names = new HashSet<>(eventNames);
        Set<Name> otherNames = new HashSet<>(((EventInNameCollectionPredicate) other).eventNames);

        return names.equals(otherNames);
    }
}
