package seedu.ccacommander.model;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a version of CcaCommander
 */
public class CcaCommanderVersion {
    private String commitMessage;
    private ReadOnlyCcaCommander versionCapture;

    /**
     * Creates a {@code CcaCommanderVersion} with the specified {@code commitMessage} and {@code versionCaptures}.
     */
    public CcaCommanderVersion(String commitMessage, ReadOnlyCcaCommander versionCapture) {
        requireAllNonNull(commitMessage, versionCapture);

        this.commitMessage = commitMessage;
        this.versionCapture = new CcaCommander(versionCapture);
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public ReadOnlyCcaCommander getVersionCapture() {
        return versionCapture;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CcaCommanderVersion
                && ((CcaCommanderVersion) other).versionCapture.equals(versionCapture)
                && ((CcaCommanderVersion) other).commitMessage.equals(commitMessage));
    }
}
