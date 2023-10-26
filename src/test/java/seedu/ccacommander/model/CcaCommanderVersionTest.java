package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
public class CcaCommanderVersionTest {

    @Test
    public void constructor_validInput_instanceCreated() {
        String commitMessage = "Initial commit";
        ReadOnlyCcaCommander versionCapture = new CcaCommander();

        CcaCommanderVersion version = new CcaCommanderVersion(commitMessage, versionCapture);

        assertEquals(commitMessage, version.getCommitMessage());
        assertEquals(versionCapture, version.getVersionCapture());
    }

    @Test
    public void getters_returnCorrectValues() {
        String commitMessage = "Test commit";
        ReadOnlyCcaCommander versionCapture = new CcaCommander();
        CcaCommanderVersion version = new CcaCommanderVersion(commitMessage, versionCapture);

        assertEquals(commitMessage, version.getCommitMessage());
        assertEquals(versionCapture, version.getVersionCapture());
    }

    @Test
    public void equals_equalInstances_true() {
        String commitMessage = "Test commit";
        ReadOnlyCcaCommander versionCapture = new CcaCommander();
        CcaCommanderVersion version1 = new CcaCommanderVersion(commitMessage, versionCapture);
        CcaCommanderVersion version2 = new CcaCommanderVersion(commitMessage, versionCapture);

        assertEquals(version1, version2);
    }

    @Test
    public void equals_differentInstances_false() {
        String commitMessage1 = "Test commit 1";
        String commitMessage2 = "Test commit 2";
        ReadOnlyCcaCommander versionCapture1 = new CcaCommander();
        ReadOnlyCcaCommander versionCapture2 = new CcaCommander();
        CcaCommanderVersion version1 = new CcaCommanderVersion(commitMessage1, versionCapture1);
        CcaCommanderVersion version2 = new CcaCommanderVersion(commitMessage2, versionCapture2);

        assertNotEquals(version1, version2);
    }

    @Test
    public void equals_sameInstance_true() {
        String commitMessage = "Test commit";
        ReadOnlyCcaCommander versionCapture = new CcaCommander();
        CcaCommanderVersion version = new CcaCommanderVersion(commitMessage, versionCapture);

        assertEquals(version, version);
    }
}
