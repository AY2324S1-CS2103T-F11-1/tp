package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class VersionCapturesTest {
    @Test
    public void constructor_validInputs_success() {
        int currentPointer = 0;
        List<String> versionCaptures = Arrays.asList("Version1", "Version2");
        VersionCaptures version = new VersionCaptures(currentPointer, versionCaptures);

        assertEquals(currentPointer, version.getCurrentPointer());
        assertEquals(versionCaptures, version.getVersionCaptures());
    }

    @Test
    public void equals_equalObjects_true() {
        List<String> versionCaptures1 = Arrays.asList("Version1", "Version2");
        VersionCaptures version1 = new VersionCaptures(0, versionCaptures1);
        VersionCaptures version2 = new VersionCaptures(0, versionCaptures1);

        assertTrue(version1.equals(version2));
    }

    @Test
    public void equals_differentStateCaptures_false() {
        VersionCaptures version1 = new VersionCaptures(0, Arrays.asList("Version1", "Version2"));
        VersionCaptures version2 = new VersionCaptures(0, Arrays.asList("Version1", "DifferentVersion"));

        assertFalse(version1.equals(version2));
    }

    @Test
    public void equals_differentCurrentIndex_false() {
        VersionCaptures version1 = new VersionCaptures(0, Arrays.asList("Version1", "Version2"));
        VersionCaptures version2 = new VersionCaptures(1, Arrays.asList("Version1", "Version2"));

        assertFalse(version1.equals(version2));
    }

    @Test
    public void equals_differentType_false() {
        VersionCaptures version = new VersionCaptures(0, Arrays.asList("Version1", "Version2"));
        Object otherObject = new Object();

        assertFalse(version.equals(otherObject));
    }
}
