package seedu.ccacommander.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.ReadOnlyCcaCommander;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.EventDate;
import seedu.ccacommander.model.event.Location;
import seedu.ccacommander.model.member.Address;
import seedu.ccacommander.model.member.Email;
import seedu.ccacommander.model.member.Gender;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.Phone;
import seedu.ccacommander.model.shared.Name;
import seedu.ccacommander.model.tag.Tag;

/**
 * Contains utility methods for populating {@code CcaCommander} with sample data.
 */
public class SampleDataUtil {
    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Gender("Male"),
                    Optional.of(new Phone("87438807")),
                    Optional.of(new Email("alexyeoh@example.com")),
                    Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                    getTagSet("friends")),
            new Member(new Name("Bernice Yu"), new Gender("Female"),
                    Optional.of(new Phone("99272758")),
                    Optional.of(new Email("berniceyu@example.com")),
                    Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                    getTagSet("colleagues", "friends")),
            new Member(new Name("Charlotte Oliveiro"), new Gender("Female"),
                    Optional.of(new Phone("93210283")),
                    Optional.of(new Email("charlotte@example.com")),
                    Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                    getTagSet("neighbours")),
            new Member(new Name("David Li"), new Gender("Male"),
                    Optional.of(new Phone("91031282")),
                    Optional.of(new Email("lidavid@example.com")),
                    Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                    getTagSet("family")),
            new Member(new Name("Irfan Ibrahim"), new Gender("Male"),
                    Optional.of(new Phone("92492021")),
                    Optional.of(new Email("irfan@example.com")),
                    Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                    getTagSet("classmates")),
            new Member(new Name("Roy Balakrishnan"), new Gender("Male"),
                    Optional.of(new Phone("92624417")),
                    Optional.of(new Email("royb@example.com")),
                    Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                    getTagSet("colleagues"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Aurora Borealis"),
                    new EventDate("2023-11-30"), new Location("Greenland"),
                    getTagSet("nature")),
            new Event(new Name("Boxing Day"),
                    new EventDate("2023-12-26"), new Location("Ridge View Residential College"),
                    getTagSet("rvrc")),
            new Event(new Name("Chinese New Year"),
                    new EventDate("2024-02-10"), new Location("Communal Hall"),
                    getTagSet("holiday")),
            new Event(new Name("Dog Day"),
                    new EventDate("2024-08-26"), new Location("UTown Pitstop"),
                    getTagSet("animals")),
            new Event(new Name("Echo Day"),
                    new EventDate("2023-05-05"), new Location("Batu Cave"),
                    getTagSet("nature", "overseas")),
            new Event(new Name("Festival"),
                    new EventDate("2023-10-05"), new Location("Clementi 321"),
                    getTagSet("holiday")),
        };
    }

    public static ReadOnlyCcaCommander getSampleCcaCommander() {
        CcaCommander sampleCcaCommander = new CcaCommander();
        for (Member sampleMember : getSampleMembers()) {
            sampleCcaCommander.createMember(sampleMember);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleCcaCommander.createEvent(sampleEvent);
        }
        return sampleCcaCommander;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
