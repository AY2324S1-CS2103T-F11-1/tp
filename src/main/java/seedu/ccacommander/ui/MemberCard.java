package seedu.ccacommander.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ccacommander.model.member.Member;

/**
 * An UI component that displays information of a {@code Member}.
 */
public class MemberCard extends UiPart<Region> {

    private static final String FXML = "MemberListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Member member;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label gender;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label memberHours;
    @FXML
    private Label memberRemark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code MemberCode} with the given {@code Member} and index to display.
     */
    public MemberCard(Member member, int displayedIndex, boolean isViewEventCommand) {
        super(FXML);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().name);
        gender.setText(member.getGender().toString());
        phone.setText(member.getPhone().value);
        address.setText(member.getAddress().value);
        email.setText(member.getEmail().value);
        memberHours.setText("Hours: " + member.getHours().toString());
        memberRemark.setText("Remark: " + member.getRemark());
        if (!isViewEventCommand) {
            memberHours.setVisible(false);
        }
        if (!isViewEventCommand) {
            memberRemark.setVisible(false);
        }


        member.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
