package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.dto.IssueDto;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IssueTest {

    private User getDefaultUser() {
        return new User("sehwan", "test", "test" );
    }

    private Issue getIssue() {
        return new Issue("test title", "test comment", getDefaultUser());
    }

    @Test
    public void update_success() throws Exception {
        Issue issue = getIssue();
        issue.update(getDefaultUser(),
                new IssueDto()
                .setTitle("new title")
                .setComment("new comment"));

        assertThat(issue.getTitle(), is("new title"));
        assertThat(issue.getComment(), is("new comment"));
    }

    @Test(expected = UnAuthenticationException.class)
    public void update_fail() throws Exception {
        User user = new User("sehwan2", "test9", "test");

        Issue issue = getIssue();
        issue.update(user,
                new IssueDto()
                        .setTitle("new title")
                        .setComment("new comment"));
    }
}
