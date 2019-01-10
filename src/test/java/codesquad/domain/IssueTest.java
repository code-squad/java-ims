package codesquad.domain;

import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import org.junit.Test;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueTest {
    private static final Logger log = getLogger(IssueTest.class);

    public static final Issue NEW_ISSUE = new Issue("제목입니다.", "내용입니다.", UserTest.JAVAJIGI);
    public static final Issue UPDATING_ISSSUE = new Issue("업데이트 제목", "업데이트 내용",null);

    @Test
    public void update_success() {
        NEW_ISSUE.modify(UPDATING_ISSSUE._toIssueDto(),UserTest.JAVAJIGI);
        assertThat(NEW_ISSUE._toIssueDto().getSubject(), is(UPDATING_ISSSUE._toIssueDto().getSubject()));
    }

    @Test(expected = UnAuthenticationException.class)
    public void update_no_login() {
        NEW_ISSUE.modify(UPDATING_ISSSUE._toIssueDto(),null);
    }

    @Test (expected = UnAuthorizedException.class)
    public void update_by_other_user() {
        NEW_ISSUE.modify(UPDATING_ISSSUE._toIssueDto(),UserTest.SANJIGI);
    }

    @Test
    public void delete_success() {
        NEW_ISSUE.delete(UserTest.JAVAJIGI);
        assertTrue(NEW_ISSUE._toIssueDto().isDeleted());
    }

    @Test (expected = UnAuthorizedException.class)
    public void delete_other() {
        NEW_ISSUE.delete(UserTest.SANJIGI);
    }
}
