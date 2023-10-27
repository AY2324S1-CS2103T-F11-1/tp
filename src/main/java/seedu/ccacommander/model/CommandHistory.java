package seedu.ccacommander.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

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
        if (this.commandHistoryList.size() == 0 ||
                this.commandHistoryList.get(this.commandHistoryList.size() - 1) != command) {
            this.commandHistoryList.add(command);
            this.currentCommandPointer = this.commandHistoryList.size();
        }
    }

    /**
     * @return if there is a next command.
     */
    public boolean hasNextCommand() {
        boolean hasCommands = this.commandHistoryList.size() > 0;
        boolean pointerHasNext = this.currentCommandPointer < this.commandHistoryList.size() - 1;
        return pointerHasNext && hasCommands;

    }
    /**
     * @return if there is a previous command.
     */
    public boolean hasPreviousCommand() {
        boolean hasCommands = this.commandHistoryList.size() > 0;
        boolean pointerHasPrevious = this.currentCommandPointer > 0;
        return pointerHasPrevious && hasCommands;
    }

    /**
     * @return next command in the {@code commandHistoryList} based on the {@code currentCommandPointer}
     */
    public String getNextCommand() {
        if (hasNextCommand()) {
            this.currentCommandPointer++;
            return this.commandHistoryList.get(currentCommandPointer);
        }
        return null;
    }

    /**
     * @return previous command in the {@code commandHistoryList} based on the {@code currentCommandPointer}
     */
    public String getPreviousCommand() {
        if (hasPreviousCommand()) {
            this.currentCommandPointer--;
            return this.commandHistoryList.get(currentCommandPointer);
        }
        return null;
    }

}