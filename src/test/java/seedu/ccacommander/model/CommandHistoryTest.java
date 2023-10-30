package seedu.ccacommander.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CommandHistoryTest {
    public static final String FIRST_COMMAND = "FIRST COMMAND";
    public static final String SECOND_COMMAND = "SECOND COMMAND";
    public static final String COMMAND_WITH_WHITESPACE = "    COMMAND WITH LEADING AND TRAILING WHITESPACE    ";
    public static final String COMMAND_WITHOUT_WHITESPACE = "COMMAND WITH LEADING AND TRAILING WHITESPACE";

    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory();
    }

    @Test
    public void constructor_hasNoCommands() {
        assertFalse(commandHistory.hasNextCommand());
        assertFalse(commandHistory.hasPreviousCommand());
        assertFalse(commandHistory.isLastCommand());
    }

    @Test
    public void addCommand_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandHistory.addCommand(null));
    }

    @Test
    public void addCommand_onlyAddsTrimmedCommands() {
        commandHistory.addCommand(COMMAND_WITH_WHITESPACE);
        commandHistory.addCommand("     ");
        String previousCommand = commandHistory.getPreviousCommand();

        assertTrue(previousCommand.equals(COMMAND_WITHOUT_WHITESPACE));
    }

    @Test
    public void addCommand_doesNotAddDuplicateCommand() {
        commandHistory.addCommand(FIRST_COMMAND);
        commandHistory.addCommand(FIRST_COMMAND);
        commandHistory.getPreviousCommand();

        assertFalse(commandHistory.hasPreviousCommand());
    }

    @Test
    public void isLastCommandEqualCommand_oneCommandAdded_returnsTrue() {
        commandHistory.addCommand(FIRST_COMMAND);
        assertTrue(commandHistory.isLastCommandEqualCommand(FIRST_COMMAND));
    }


    @Test
    public void isLastCommandEqualCommand_oneCommandAdded_returnsFalse() {
        commandHistory.addCommand(FIRST_COMMAND);
        assertFalse(commandHistory.isLastCommandEqualCommand(SECOND_COMMAND));
    }

    @Test
    public void hasNextCommand_noCommandsAdded_returnsFalse() {
        assertFalse(commandHistory.hasNextCommand());
    }

    @Test
    public void hasNextCommand_hasCommandsAdded_returnsTrue() {
        commandHistory.addCommand(FIRST_COMMAND);
        commandHistory.addCommand(SECOND_COMMAND);
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();

        assertTrue(commandHistory.hasNextCommand());
    }

    @Test
    public void hasPreviousCommand_noCommandsAdded_returnsFalse() {
        assertFalse(commandHistory.hasPreviousCommand());
    }

    @Test
    public void hasPreviousCommand_hasCommandsAdded_returnsTrue() {
        commandHistory.addCommand(FIRST_COMMAND);
        assertTrue(commandHistory.hasPreviousCommand());
    }

    @Test
    public void getNextCommand_hasNextCommand_returnsNextCommand() {
        commandHistory.addCommand(FIRST_COMMAND);
        commandHistory.addCommand(SECOND_COMMAND);
        commandHistory.getPreviousCommand();
        commandHistory.getPreviousCommand();

        String nextCommand = commandHistory.getNextCommand();
        assertEquals(SECOND_COMMAND, nextCommand);
    }

    @Test
    public void getNextCommand_throwsAssertionError() {
        AssertionError thrown = assertThrows(AssertionError.class, () -> commandHistory.getNextCommand());
        assertTrue(thrown.getMessage().equals("currentCommandPointer is at the newest command already"));
    }


    @Test
    public void getPreviousCommand_hasPreviousCommand_returnsPreviousCommand() {
        commandHistory.addCommand(FIRST_COMMAND);

        String previousCommand = commandHistory.getPreviousCommand();
        assertEquals(FIRST_COMMAND, previousCommand);
    }

    @Test
    public void getPreviousCommand_throwsAssertionError() {
        AssertionError thrown = assertThrows(AssertionError.class, () -> commandHistory.getPreviousCommand());
        assertTrue(thrown.getMessage().equals("currentCommandPointer is at the oldest command already"));
    }

    @Test
    public void isLastCommand_returnsTrue() {
        commandHistory.addCommand(FIRST_COMMAND);
        commandHistory.getPreviousCommand();

        assertTrue(commandHistory.isLastCommand());
    }

}
