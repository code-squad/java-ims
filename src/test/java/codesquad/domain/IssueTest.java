package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

public class IssueTest extends BaseTest {
    public static final Issue FIRST_ISSUE = new Issue(1L, "First issue test", "first comment", UserTest.USER,false);
    public static final Issue SECOND_ISSUE = new Issue(2L, "Second issue test", "Second comment", UserTest.USER, false);
    private Issue issue;

    @Before
    public void setUp() {
        Issue issue = new Issue(1L,"test issue", "test comment");
        this.issue = issue;
    }

    @Test
    public void create() {
        Issue issue = new Issue(1L,"test issue", "test comment");
        softly.assertThat(issue.getId()).isEqualTo(1);
        softly.assertThat(issue.getSubject()).isEqualTo("test issue");
        softly.assertThat(issue.getComment()).isEqualTo("test comment");
    }

    @Test
    public void update() {
        FIRST_ISSUE.update(UserTest.USER, issue);
        softly.assertThat(issue.getSubject()).isEqualTo("test issue");
        softly.assertThat(issue.getComment()).isEqualTo("test comment");
    }

    @Test(expected = NullPointerException.class)
    public void update_no_login() {
        FIRST_ISSUE.update(null, issue);
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_worng_id() {
        FIRST_ISSUE.update(UserTest.OTHER_USER, issue);
    }

    @Test
    public void delete() {
        FIRST_ISSUE.delete(UserTest.USER);
        softly.assertThat(FIRST_ISSUE.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_OtherUser() {
        FIRST_ISSUE.delete(UserTest.OTHER_USER);;
    }

    @Test(expected = NullPointerException.class)
    public void delete_no_login() {
        FIRST_ISSUE.delete(null);
    }
}
