package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name");

    public static Issue ISSUE1 = new Issue("질문이있어요1","질문내용이에요1");
    public static Issue ISSUE2 = new Issue("질문이있어요2","질문내용이에요2");


    private Issue newIssue(Issue issue) {
        return new Issue(issue.getSubject(), issue.getComment());
    }

    @Test
    public void updateTest() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.update(JAVAJIGI, ISSUE2.toDto());
        assertThat(originalIssue.getSubject(), is(ISSUE2.getSubject()));
        assertThat(originalIssue.getComment(), is(ISSUE2.getComment()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void updateTest_Fail_anotherUser() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.update(SANJIGI, ISSUE2.toDto());
    }

    @Test(expected = UnAuthorizedException.class)
    public void updateTest_Fail_noLogin() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.update(null, ISSUE2.toDto());
    }

    @Test
    public void deleteTest() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.delete(JAVAJIGI);
        assertTrue(originalIssue.isDeleted());
    }

    @Test(expected = UnAuthorizedException.class)
    public void deleteTest_Fail_anotherUser() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.delete(SANJIGI);
    }

    @Test(expected = UnAuthorizedException.class)
    public void deleteTest_Fail_noLogin() {
        Issue originalIssue = newIssue(ISSUE1);
        originalIssue.writeBy(JAVAJIGI);
        originalIssue.delete(null);
    }

}
