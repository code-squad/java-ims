package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import codesquad.domain.issue.Issue;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest extends BaseTest {
    public static final Issue ORIGINAL_ISSUE = new Issue(1L,"subject","comment", JAVAJIGI);
    public static final Issue UPDATED_ISSUE = new Issue("updatedSubject","updatedComment",JAVAJIGI);
    public static final Issue UPDATED_ISSUE_2 = new Issue("updatedSubject","updatedComment",SANJIGI);

    @Test
    public void update_success() {
        ORIGINAL_ISSUE.update(JAVAJIGI, UPDATED_ISSUE);
        softly.assertThat(ORIGINAL_ISSUE.getSubject()).isEqualTo(UPDATED_ISSUE.getSubject());
        softly.assertThat(ORIGINAL_ISSUE.getComment()).isEqualTo(UPDATED_ISSUE.getComment());
    }

    @Test(expected = CannotUpdateException.class)
    public void update_fail() {
        ORIGINAL_ISSUE.update(SANJIGI, UPDATED_ISSUE);
    }

    @Test
    public void delete_success() {
        ORIGINAL_ISSUE.delete(JAVAJIGI);
        softly.assertThat(ORIGINAL_ISSUE.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() {
        ORIGINAL_ISSUE.delete(SANJIGI);
    }
}
