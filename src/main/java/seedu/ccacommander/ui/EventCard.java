package seedu.ccacommander.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ccacommander.model.event.Event;

/**
 * A UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label eventLocation;
    @FXML
    private Label eventDate;

    @FXML
    private Label eventHours;
    @FXML
    private Label eventRemark;

    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code EventCode} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex, boolean isViewMemberCommand) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().name);
        eventLocation.setText(event.getLocation().value);
        eventDate.setText(event.getDate().toString());
        event.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        eventHours.setText("Hours: " + event.getHours().toString());
        eventRemark.setText("Remark: " + event.getRemark());
        if (!isViewMemberCommand) {
            eventHours.setVisible(false);
        }
        if (!isViewMemberCommand) {
            eventRemark.setVisible(false);
        }
    }
}
