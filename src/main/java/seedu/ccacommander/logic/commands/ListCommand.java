package seedu.ccacommander.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.ccacommander.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import seedu.ccacommander.model.Model;
import seedu.ccacommander.ui.EventListPanel;
import seedu.ccacommander.ui.MemberListPanel;



/**
 * Lists all members and events in CcaCommander to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all members and events";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        MemberListPanel.setIsViewEventCommand(false);
        EventListPanel.setIsViewMemberCommand(false);

        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
