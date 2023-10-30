package seedu.ccacommander.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ccacommander.model.util.SampleDataUtil.getSampleCcaCommander;
import static seedu.ccacommander.model.util.SampleDataUtil.getSampleEvents;
import static seedu.ccacommander.model.util.SampleDataUtil.getSampleMembers;
import static seedu.ccacommander.model.util.SampleDataUtil.getTagSet;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ccacommander.model.CcaCommander;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.event.EventDate;
import seedu.ccacommander.model.event.Location;
import seedu.ccacommander.model.member.Address;
import seedu.ccacommander.model.member.Email;
import seedu.ccacommander.model.member.Gender;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.model.member.Phone;
import seedu.ccacommander.model.shared.Name;

class SampleDataUtilTest {

    @Test
    void getSampleMembers_returnsValidMembers() {
        Member[] sampleMembers = getSampleMembers();
        Member alex = new Member(new Name("Alex Yeoh"),
                new Gender("Male"),
                Optional.of(new Phone("87438807")),
                Optional.of(new Email("alexyeoh@example.com")),
                Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                getTagSet("friends"));
        Member bernice = new Member(new Name("Bernice Yu"),
                new Gender("Female"),
                Optional.of(new Phone("99272758")),
                Optional.of(new Email("berniceyu@example.com")),
                Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                getTagSet("colleagues", "friends"));
        Member charlotte = new Member(new Name("Charlotte Oliveiro"),
                new Gender("Female"),
                Optional.of(new Phone("93210283")),
                Optional.of(new Email("charlotte@example.com")),
                Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                getTagSet("neighbours"));
        Member david = new Member(new Name("David Li"),
                new Gender("Male"),
                Optional.of(new Phone("91031282")),
                Optional.of(new Email("lidavid@example.com")),
                Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                getTagSet("family"));
        Member irfan = new Member(new Name("Irfan Ibrahim"),
                new Gender("Male"),
                Optional.of(new Phone("92492021")),
                Optional.of(new Email("irfan@example.com")),
                Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                getTagSet("classmates"));
        Member roy = new Member(new Name("Roy Balakrishnan"),
                new Gender("Male"),
                Optional.of(new Phone("92624417")),
                Optional.of(new Email("royb@example.com")),
                Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                getTagSet("colleagues"));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(alex::equals));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(bernice::equals));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(charlotte::equals));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(david::equals));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(irfan::equals));
        assertTrue(Arrays.stream(sampleMembers).anyMatch(roy::equals));
    }

    @Test
    void getSampleEvents_returnsValidEvents() {
        Event[] sampleEvents = getSampleEvents();
        Event aurora = new Event(new Name("Aurora Borealis"),
                new EventDate("2023-11-30"), new Location("Greenland"),
                getTagSet("nature"));
        Event boxing = new Event(new Name("Boxing Day"),
                new EventDate("2023-12-26"), new Location("Ridge View Residential College"),
                getTagSet("rvrc"));
        Event cny = new Event(new Name("Chinese New Year"),
                new EventDate("2024-02-10"), new Location("Communal Hall"),
                getTagSet("holiday"));
        Event dog = new Event(new Name("Dog Day"),
                new EventDate("2024-08-26"), new Location("UTown Pitstop"),
                getTagSet("animals"));
        Event echo = new Event(new Name("Echo Day"),
                new EventDate("2023-05-05"), new Location("Batu Cave"),
                getTagSet("nature", "overseas"));
        Event festival = new Event(new Name("Festival"),
                new EventDate("2023-10-05"), new Location("Clementi 321"),
                getTagSet("holiday"));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(aurora::equals));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(boxing::equals));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(cny::equals));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(dog::equals));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(echo::equals));
        assertTrue(Arrays.stream(sampleEvents).anyMatch(festival::equals));

    }

    @Test
    void getSampleCcaCommander_returnsValidMembersAndEvents() {
        Member alex = new Member(new Name("Alex Yeoh"),
                new Gender("Male"),
                Optional.of(new Phone("87438807")),
                Optional.of(new Email("alexyeoh@example.com")),
                Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                getTagSet("friends"));
        Member bernice = new Member(new Name("Bernice Yu"),
                new Gender("Female"),
                Optional.of(new Phone("99272758")),
                Optional.of(new Email("berniceyu@example.com")),
                Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                getTagSet("colleagues", "friends"));
        Member charlotte = new Member(new Name("Charlotte Oliveiro"),
                new Gender("Female"),
                Optional.of(new Phone("93210283")),
                Optional.of(new Email("charlotte@example.com")),
                Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                getTagSet("neighbours"));
        Member david = new Member(new Name("David Li"),
                new Gender("Male"),
                Optional.of(new Phone("91031282")),
                Optional.of(new Email("lidavid@example.com")),
                Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                getTagSet("family"));
        Member irfan = new Member(new Name("Irfan Ibrahim"),
                new Gender("Male"),
                Optional.of(new Phone("92492021")),
                Optional.of(new Email("irfan@example.com")),
                Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                getTagSet("classmates"));
        Member roy = new Member(new Name("Roy Balakrishnan"),
                new Gender("Male"),
                Optional.of(new Phone("92624417")),
                Optional.of(new Email("royb@example.com")),
                Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                getTagSet("colleagues"));
        Event aurora = new Event(new Name("Aurora Borealis"),
                new EventDate("2023-11-30"), new Location("Greenland"),
                getTagSet("nature"));
        Event boxing = new Event(new Name("Boxing Day"),
                new EventDate("2023-12-26"), new Location("Ridge View Residential College"),
                getTagSet("rvrc"));
        Event cny = new Event(new Name("Chinese New Year"),
                new EventDate("2024-02-10"), new Location("Communal Hall"),
                getTagSet("holiday"));
        Event dog = new Event(new Name("Dog Day"),
                new EventDate("2024-08-26"), new Location("UTown Pitstop"),
                getTagSet("animals"));
        Event echo = new Event(new Name("Echo Day"),
                new EventDate("2023-05-05"), new Location("Batu Cave"),
                getTagSet("nature", "overseas"));
        Event festival = new Event(new Name("Festival"),
                new EventDate("2023-10-05"), new Location("Clementi 321"),
                getTagSet("holiday"));
        CcaCommander ccaCommander = (CcaCommander) getSampleCcaCommander();
        assertTrue(ccaCommander.hasMember(alex));
        assertTrue(ccaCommander.hasMember(bernice));
        assertTrue(ccaCommander.hasMember(charlotte));
        assertTrue(ccaCommander.hasMember(david));
        assertTrue(ccaCommander.hasMember(irfan));
        assertTrue(ccaCommander.hasMember(roy));
        assertTrue(ccaCommander.hasEvent(aurora));
        assertTrue(ccaCommander.hasEvent(boxing));
        assertTrue(ccaCommander.hasEvent(cny));
        assertTrue(ccaCommander.hasEvent(dog));
        assertTrue(ccaCommander.hasEvent(echo));
        assertTrue(ccaCommander.hasEvent(festival));
    }
}
