package seedu.ccacommander.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.ccacommander.logic.commands.CommandResult;
import seedu.ccacommander.logic.commands.exceptions.CommandException;
import seedu.ccacommander.logic.parser.exceptions.ParseException;
import seedu.ccacommander.model.CommandHistory;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final CommandHistory COMMAND_HISTORY = new CommandHistory();
    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
                if (COMMAND_HISTORY.hasPreviousCommand()) {
                    String previousCommand = COMMAND_HISTORY.getPreviousCommand();
                    commandTextField.setText(previousCommand);
                    commandTextField.positionCaret(previousCommand.length());
                }
                break;
            case DOWN:
                if (COMMAND_HISTORY.hasNextCommand()) {
                    String nextCommand = COMMAND_HISTORY.getNextCommand();
                    commandTextField.setText(nextCommand);
                    commandTextField.positionCaret(nextCommand.length());
                } else if (COMMAND_HISTORY.isLastCommand()) {
                    COMMAND_HISTORY.resetPointer();
                    commandTextField.setText("");
                }
                break;
            case BACK_SPACE:
            case ENTER:
                COMMAND_HISTORY.resetPointer();
                break;
            default:
                break;
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            COMMAND_HISTORY.addCommand(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.ccacommander.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
