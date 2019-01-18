package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

public class AnswerTest extends BaseTest {
    public static final Answer FIRST_ANSWER = new Answer(1L, "First answer test", UserTest.USER, IssueTest.FIRST_ISSUE,false);
    public static final Answer SECOND_ANSWER = new Answer(2L, "second answer test",  UserTest.USER, IssueTest.FIRST_ISSUE,false);

    @Test
    public void update_LoginUser() {
        FIRST_ANSWER.update("update", UserTest.USER);
        softly.assertThat(FIRST_ANSWER.getComment()).isEqualTo("update");
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_OtherUser() {
        FIRST_ANSWER.update("update", UserTest.OTHER_USER);
    }

    @Test
    public void delete_LoginUser() {
        FIRST_ANSWER.delete(UserTest.USER);
        softly.assertThat(FIRST_ANSWER.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_OtherUser() {
        FIRST_ANSWER.delete(UserTest.OTHER_USER);
    }
}
