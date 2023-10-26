package seedu.ccacommander.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // Shared prefixes
    public static final Prefix PREFIX_NAME = new Prefix("n/");

    // Member prefixes
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");

    // Event prefixes
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");

    // Attendance prefixes
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_HOURS = new Prefix("h/");
    public static final Prefix PREFIX_MEMBER = new Prefix("m/");
    public static final Prefix PREFIX_EVENT = new Prefix("e/");
}
