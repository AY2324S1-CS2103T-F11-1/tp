package seedu.ccacommander.logic.commands;

import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ccacommander.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ccacommander.testutil.TypicalCcaCommander.getTypicalCcaCommander;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ccacommander.logic.Messages;
import seedu.ccacommander.model.Model;
import seedu.ccacommander.model.ModelManager;
import seedu.ccacommander.model.UserPrefs;
import seedu.ccacommander.model.member.Member;
import seedu.ccacommander.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateMemberCommand}.
 */
public class CreateMemberCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCcaCommander(), new UserPrefs());
    }

    @Test
    public void execute_newMember_success() {
        Member validMember = new MemberBuilder().build();

        String commitMessage = String.format(CreateMemberCommand.MESSAGE_COMMIT, validMember.getName());
        Model expectedModel = new ModelManager(model.getCcaCommander(), new UserPrefs());
        expectedModel.createMember(validMember);
        expectedModel.commit(commitMessage);

        assertCommandSuccess(new CreateMemberCommand(validMember), model,
                String.format(CreateMemberCommand.MESSAGE_SUCCESS, Messages.format(validMember)),
                expectedModel);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member memberInList = model.getCcaCommander().getMemberList().get(0);
        assertCommandFailure(new CreateMemberCommand(memberInList), model,
                CreateMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

}
