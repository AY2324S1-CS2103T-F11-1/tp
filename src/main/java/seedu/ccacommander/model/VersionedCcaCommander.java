package seedu.ccacommander.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.ccacommander.model.exceptions.RedoStateException;
import seedu.ccacommander.model.exceptions.UndoStateException;

/**
 * A CcaCommander with versions implemented.
 */
public class VersionedCcaCommander extends CcaCommander {
    public static final String MESSAGE_FIRST_COMMIT = "Saved data is loaded.";

    private List<CcaCommanderVersion> ccaCommanderVersionList;
    private int versionPointer;

    /**
     * Creates a {@code VersionedCcaCommander} using the Members in the {@code toCopy}.
     * @param toCopy
     */
    public VersionedCcaCommander(ReadOnlyCcaCommander toCopy) {
        super(toCopy);

        CcaCommanderVersion initialVersion = new CcaCommanderVersion(MESSAGE_FIRST_COMMIT, this);
        this.ccaCommanderVersionList = new ArrayList<>();
        this.ccaCommanderVersionList.add(initialVersion);
        this.versionPointer = 0;
    }

    /**
     * Saves the current state of the {@code CcaCommander} with the associated {@code commitMessage}.
     *
     * @throws NullPointerException if the {@code commitMessage} is null.
     */
    public void commit(String commitMessage) throws NullPointerException {
        requireNonNull(commitMessage);

        purgeRedundantVersions();
        CcaCommanderVersion state = new CcaCommanderVersion(commitMessage, this);
        updateVersion(state);
    }

    private void purgeRedundantVersions() {
        this.ccaCommanderVersionList.subList(this.versionPointer + 1, this.ccaCommanderVersionList.size()).clear();
    }

    private void updateVersion(CcaCommanderVersion version) {
        this.ccaCommanderVersionList.add(version);
        this.versionPointer++;
    }

    /**
     * Undoes the most recent undoable command.
     *
     * @return the commit message of the undone command.
     * @throws UndoStateException if there are no commands to undo.
     */
    public String undo() throws UndoStateException {
        if (!canUndo()) {
            throw new UndoStateException();
        }

        CcaCommanderVersion currentVersion = this.ccaCommanderVersionList.get(this.versionPointer);
        CcaCommanderVersion targetVersion = this.ccaCommanderVersionList.get(this.versionPointer - 1);
        resetData(targetVersion.getVersionCapture());
        this.versionPointer--;
        return currentVersion.getCommitMessage();
    }

    /**
     * Redoes the most recent undone command.
     *
     * @return the commit message of the redone command.
     * @throws RedoStateException if there are no commands to redo.
     */
    public String redo() throws RedoStateException {
        if (!canRedo()) {
            throw new RedoStateException();
        }

        CcaCommanderVersion targetVersion = ccaCommanderVersionList.get(versionPointer + 1);
        resetData(targetVersion.getVersionCapture());
        this.versionPointer++;
        return targetVersion.getCommitMessage();
    }


    /**
     * Returns true if there exists a {@code Command} that can be undone.
     */
    public boolean canUndo() {
        return this.versionPointer > 0;
    }

    /**
     * Returns true if there exists a {@code Command} that can be redone.
     */
    public boolean canRedo() {
        return this.versionPointer < ccaCommanderVersionList.size() - 1;
    }

    /**
     * Returns a summary of all {@code Command}s currently recorded by this {@code VersionedCcaCommander}.
     */
    public VersionCaptures viewVersionCaptures() {
        return new VersionCaptures(versionPointer, ccaCommanderVersionList.stream().map(version ->
                version.getCommitMessage()).collect(Collectors.toUnmodifiableList()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof VersionedCcaCommander
                && ((VersionedCcaCommander) other).versionPointer == this.versionPointer
                && ((VersionedCcaCommander) other).ccaCommanderVersionList.equals(this.ccaCommanderVersionList)
                && super.equals(other));
    }
}
