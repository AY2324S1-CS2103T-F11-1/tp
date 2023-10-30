package seedu.ccacommander.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ccacommander.commons.core.LogsCenter;
import seedu.ccacommander.model.member.Member;

/**
 * Panel containing the list of members.
 */
public class MemberListPanel extends UiPart<Region> {
    private static boolean isViewEventCommand = false;
    private static final String FXML = "MemberListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemberListPanel.class);

    @FXML
    private ListView<Member> memberListView;

    /**
     * Creates a {@code MemberListPanel} with the given {@code ObservableList}.
     */
    public MemberListPanel(ObservableList<Member> memberList) {
        super(FXML);
        memberListView.setItems(memberList);
        memberListView.setCellFactory(listView -> new MemberListViewCell());
    }

    public static void setIsViewEventCommand(boolean isViewEventCommand) {
        MemberListPanel.isViewEventCommand = isViewEventCommand;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code MemberCard}.
     */
    class MemberListViewCell extends ListCell<Member> {
        @Override
        protected void updateItem(Member member, boolean empty) {
            super.updateItem(member, empty);

            if (empty || member == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MemberCard(member, getIndex() + 1, isViewEventCommand).getRoot());
            }
        }
    }

}
