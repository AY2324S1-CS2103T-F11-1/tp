package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Member;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Member> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Member> memberList) {
        super(FXML);
        personListView.setItems(memberList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Member> {
        @Override
        protected void updateItem(Member member, boolean empty) {
            super.updateItem(member, empty);

            if (empty || member == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(member, getIndex() + 1).getRoot());
            }
        }
    }

}
