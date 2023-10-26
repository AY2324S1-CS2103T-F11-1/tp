package seedu.ccacommander.model;

import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ccacommander.commons.core.GuiSettings;
import seedu.ccacommander.commons.core.LogsCenter;
import seedu.ccacommander.model.attendance.Attendance;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;

/**
 * Represents the in-memory model of the CcaCommander data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final VersionedCcaCommander versionedCcaCommander;
    private final UserPrefs userPrefs;
    private final FilteredList<Member> filteredMembers;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Attendance> filteredAttendances;

    /**
     * Initializes a ModelManager with the given CcaCommander and userPrefs.
     */
    public ModelManager(ReadOnlyCcaCommander ccaCommander, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(ccaCommander, userPrefs);

        logger.fine("Initializing with CCACommander: " + ccaCommander + " and user prefs " + userPrefs);

        this.versionedCcaCommander = new VersionedCcaCommander(ccaCommander);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMembers = new FilteredList<>(this.versionedCcaCommander.getMemberList());
        filteredEvents = new FilteredList<>(this.versionedCcaCommander.getEventList());
        filteredAttendances = new FilteredList<>(this.versionedCcaCommander.getAttendanceList());
    }

    public ModelManager() {
        this(new CcaCommander(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCcaCommanderFilePath() {
        return userPrefs.getCcaCommanderFilePath();
    }

    @Override
    public void setCcaCommanderFilePath(Path ccaCommanderFilePath) {
        requireNonNull(ccaCommanderFilePath);
        userPrefs.setCcaCommanderFilePath(ccaCommanderFilePath);
    }

    //=========== CcaCommander ================================================================================

    @Override
    public void setCcaCommander(ReadOnlyCcaCommander ccaCommander) {
        versionedCcaCommander.resetData(ccaCommander);
    }

    @Override
    public ReadOnlyCcaCommander getCcaCommander() {
        return versionedCcaCommander;
    }

    @Override
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return versionedCcaCommander.hasMember(member);
    }

    @Override
    public void deleteMember(Member target) {
        versionedCcaCommander.removeMember(target);
    }

    @Override
    public void createMember(Member member) {
        versionedCcaCommander.createMember(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);

        versionedCcaCommander.setMember(target, editedMember);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedCcaCommander.hasEvent(event);
    }
    @Override
    public void deleteEvent(Event target) {
        versionedCcaCommander.removeEvent(target);
    }

    @Override
    public void createEvent(Event event) {
        versionedCcaCommander.createEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        versionedCcaCommander.setEvent(target, editedEvent);
    }

    @Override
    public boolean hasAttendance(Attendance attendance) {
        requireNonNull(attendance);
        return versionedCcaCommander.hasAttendance(attendance);
    }

    @Override
    public void createAttendance(Attendance attendance) {
        versionedCcaCommander.createAttendance(attendance);
        updateFilteredAttendanceList(PREDICATE_SHOW_ALL_ATTENDANCES);
    }

    @Override
    public void commit(String commitMessage) {
        versionedCcaCommander.commit(commitMessage);
    }

    @Override
    public boolean canUndo() {
        return versionedCcaCommander.canUndo();
    }

    @Override
    public String undo() {
        return versionedCcaCommander.undo();
    }

    @Override
    public boolean canRedo() {
        return versionedCcaCommander.canRedo();
    }

    @Override
    public String redo() {
        return versionedCcaCommander.redo();
    }

    @Override
    public VersionCaptures viewVersionCaptures() {
        return versionedCcaCommander.viewVersionCaptures();
    }

    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Member} backed by the internal list of
     * {@code versionedCcaCommander}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return filteredMembers;
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public ObservableList<Attendance> getFilteredAttendanceList() {
        return filteredAttendances;
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        requireNonNull(predicate);
        filteredMembers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredAttendanceList(Predicate<Attendance> predicate) {
        requireNonNull(predicate);
        filteredAttendances.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return versionedCcaCommander.equals(otherModelManager.versionedCcaCommander)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredMembers.equals(otherModelManager.filteredMembers)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }
}
