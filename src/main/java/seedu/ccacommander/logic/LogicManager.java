package seedu.ccacommander.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.ccacommander.commons.core.GuiSettings;
import seedu.ccacommander.commons.core.LogsCenter;
import seedu.ccacommander.logic.commands.Command;
import seedu.ccacommander.logic.commands.CommandResult;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.logic.parser.CcaCommanderParser;
import seedu.ccacommander.logic.parser.exceptions.ParseException;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ReadOnlyCcaCommander;
import seedu.ccacommander.model.event.Event;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CcaCommanderParser ccaCommanderParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        ccaCommanderParser = new CcaCommanderParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = ccaCommanderParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveCcaCommander(model.getCcaCommander());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCcaCommander getCcaCommander() {
        return model.getCcaCommander();
    }

    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return model.getFilteredMemberList();
    }
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public Path getCcaCommanderFilePath() {
        return model.getCcaCommanderFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}