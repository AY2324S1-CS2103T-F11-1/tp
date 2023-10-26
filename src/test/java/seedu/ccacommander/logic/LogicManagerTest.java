package seedu.ccacommander.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ccacommander.logic.Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX;
import static seedu.ccacommander.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ccacommander.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.ccacommander.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.ccacommander.testutil.Assert.assertThrows;
import static seedu.ccacommander.testutil.TypicalMembers.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ccacommander.logic.commands.CommandResult;
import seedu.ccacommander.logic.commands.CreateMemberCommand;
import seedu.ccacommander.logic.commands.ListCommand;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.logic.parser.exceptions.ParseException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.ReadOnlyCcaCommander;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.storage.JsonCcaCommanderStorage;
import seedu.ccacommander.storage.JsonUserPrefsStorage;
import seedu.ccacommander.storage.StorageManager;
import seedu.ccacommander.testutil.MemberBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCcaCommanderStorage ccaCommanderStorage =
                new JsonCcaCommanderStorage(temporaryFolder.resolve("ccacommander.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(ccaCommanderStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteMember 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredMemberList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredMemberList().remove(0));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredEventList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getCcaCommander(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an CcaCommanderStorage that throws the IOException e when saving
        JsonCcaCommanderStorage ccaCommanderStorage = new JsonCcaCommanderStorage(prefPath) {
            @Override
            public void saveCcaCommander(ReadOnlyCcaCommander ccaCommander, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(ccaCommanderStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveCcaCommander method by executing a create command
        String createMemberCommand = CreateMemberCommand.COMMAND_WORD + NAME_DESC_AMY + GENDER_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        Member expectedMember = new MemberBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.createMember(expectedMember);
        expectedModel.commit(String.format(CreateMemberCommand.MESSAGE_COMMIT, expectedMember.getName()));
        assertCommandFailure(createMemberCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
