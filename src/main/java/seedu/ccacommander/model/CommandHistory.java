package seedu.ccacommander.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The facilitator of storing commands and functions related to recalling previously entered commands.
 */
public class CommandHistory {
    private List<String> commandHistoryList;
    private int currentCommandPointer;

    /**
     * Constructs a new instance of CommandHistory.
     */
    public CommandHistory() {
        this.commandHistoryList = new ArrayList<>();
        this.currentCommandPointer = 0;
    }

    /**
     * Adds the command into {@code commandHistoryList} and sets the pointer to the new command only if the command
     * is not the latest command added.
     *
     * @param command
     */
    public void addCommand(String command) {
        requireNonNull(command);
        String trimmedCommand = command.trim();
        if (trimmedCommand.length() == 0) {
            return;
        }

        if (this.commandHistoryList.size() == 0
                || !isLastCommandEqualCommand(trimmedCommand)) {
            this.commandHistoryList.add(trimmedCommand);
            resetPointer();
        }
    }

    /**
     * Returns true if last command in {@code commandHistoryList} is equal to param command.
     *
     * @param command
     */
    public boolean isLastCommandEqualCommand(String command) {
        return this.commandHistoryList.get(this.commandHistoryList.size() - 1).equals(command);
    }

    /**
     * Returns true if there is a next command.
     * @return boolean value of whether a next command exists.
     */
    public boolean hasNextCommand() {
        boolean hasCommands = this.commandHistoryList.size() > 0;
        boolean pointerHasNext = this.currentCommandPointer < this.commandHistoryList.size() - 1;
        return pointerHasNext && hasCommands;

    }

    /**
     * Returns true if there is a previous command.
     */
    public boolean hasPreviousCommand() {
        boolean hasCommands = this.commandHistoryList.size() > 0;
        boolean pointerHasPrevious = this.currentCommandPointer > 0;
        return pointerHasPrevious && hasCommands;
    }

    /**
     * Returns next command.
     * Must be used in conjunction with {@code hasNextCommand} hence the assertion.
     */
    public String getNextCommand() {
        assert this.currentCommandPointer < this.commandHistoryList.size() - 1
                : "currentCommandPointer is at the newest command already";
        this.currentCommandPointer++;
        String nextCommand = this.commandHistoryList.get(this.currentCommandPointer);
        return nextCommand;
    }

    /**
     * Returns previous command.
     * Must be used in conjunction with {@code hasPreviousCommand} hence the assertion.
     */
    public String getPreviousCommand() {
        assert this.currentCommandPointer > 0
                : "currentCommandPointer is at the oldest command already";
        this.currentCommandPointer--;
        String command = this.commandHistoryList.get(this.currentCommandPointer);
        return command;
    }

    /**
     * Resets the pointer to after the last command (no next command available, only previous commands available)
     * This will be used to move the pointer past the last command.
     */
    public void resetPointer() {
        this.currentCommandPointer = this.commandHistoryList.size();
    }

    /**
     * Returns true if the pointer is at the last command.
     */
    public boolean isLastCommand() {
        return this.currentCommandPointer == this.commandHistoryList.size() - 1;
    }

}
