package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueTest {
    private static final Logger log = LoggerFactory.getLogger(IssueTest.class);

    private Issue issue;
    private IssueDto updateIssueInfo;
    private User writer;
    private User other;

    @Before
    public void setUp() throws Exception {
        writer = new User("colin", "1234", "colin");
        other = new User("jinbro", "1234", "jinbro");
        issue = new Issue("subject", "comment").writeBy(writer);
        updateIssueInfo = new IssueDto("modify subject", "modify comment");
    }

    @Test
    public void status_message() {
        assertThat(issue.getStatus(), is("OPEN"));
    }

    @Test
    public void update() throws Exception {
        Issue result = issue.update(writer, updateIssueInfo);
        assertThat(result.getComment(), is("modify comment"));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_fail() throws Exception {
        issue.update(other, updateIssueInfo);
    }

    @Test
    public void delete() throws Exception {
        DeleteHistory deleteHistory = issue.delete(writer);
        log.debug("delete info : {}, {}, {}, {}", deleteHistory.getContentId(), deleteHistory.getContentType(), deleteHistory.getDeletedBy(), deleteHistory.getDeleteTime());
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() throws Exception {
        issue.delete(other);
    }
}