package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IssueTest {
    private static final User WRITER = new User(1L, "boobby", "1234", "boo");
    private static final User OTHER_USER = new User(2L, "notBoobby", "1234", "I'm not boo");
    private Issue issue;

    @Before
    public void setUp() throws Exception {
        issue = new Issue(1L, "issue", "comment");
        issue.writeBy(WRITER);
    }

    @Test
    public void update_by_writer() {
        String subject = "issuuuuuuuue";
        String comment = "commeeeeeeeeent";
        IssueDto issueDto = new IssueDto(subject, comment);

        issue.update(WRITER, issueDto);

        assertThat(issue.getSubject(), is(subject));
        assertThat(issue.getComment(), is(comment));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_by_other() {
        String subject = "issuuuuuuuue";
        String comment = "commeeeeeeeeent";
        IssueDto issueDto = new IssueDto(subject, comment);

        issue.update(OTHER_USER, issueDto);
    }

    @Test
    public void isWriteBy_writer() {
        assertThat(issue.isWriteBy(WRITER), is(true));
    }

    @Test
    public void isWriteBy_other() {
        assertThat(issue.isWriteBy(OTHER_USER), is(false));
    }
}
