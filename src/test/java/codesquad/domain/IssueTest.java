package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest extends BaseTest {
    public static final Issue originalIssue = new Issue(1L,"subject","comment", JAVAJIGI);
    public static final Issue updatedIssue = new Issue("updatedSubject","updatedComment",JAVAJIGI);

    @Test
    public void update_success() {
        originalIssue.update(JAVAJIGI,updatedIssue);
        softly.assertThat(originalIssue.getSubject()).isEqualTo(updatedIssue.getSubject());
        softly.assertThat(originalIssue.getComment()).isEqualTo(updatedIssue.getComment());
    }

    @Test(expected = CannotUpdateException.class)
    public void update_fail() {
        originalIssue.update(SANJIGI, updatedIssue);
    }

    @Test
    public void delete_success() {
        originalIssue.delete(JAVAJIGI);
        softly.assertThat(originalIssue.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() {
        originalIssue.delete(SANJIGI);
    }
}
