package seedu.ccacommander.model;

import static seedu.ccacommander.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

/**
 * Captures the versions of CcaCommander.
 */
public class VersionCaptures {
    private int currentPointer;
    private List<String> versionCaptures;

    /**
     * Creates a {@code VersionCaptures} with the specified {@code currentPointer} and {@code versionCaptures}.
     */
    public VersionCaptures(int currentPointer, List<String> versionCaptures) {
        requireAllNonNull(currentPointer, versionCaptures);

        this.versionCaptures = versionCaptures;
        this.currentPointer = currentPointer;
    }

    public List<String> getVersionCaptures() {
        return this.versionCaptures;
    }
    public int getCurrentPointer() {
        return this.currentPointer;
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof VersionCaptures
                && ((VersionCaptures) other).getVersionCaptures().equals(getVersionCaptures())
                && ((VersionCaptures) other).getCurrentPointer() == getCurrentPointer());
    }
}
