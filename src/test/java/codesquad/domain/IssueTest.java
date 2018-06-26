package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueTest {
    private static final String ORIGINAL_TITLE = "original title";
    private static final String ORIGINAL_CONTENT = "original content";
    private Issue issue;
    private User writer;

    @Before
    public void setUp() {
        issue = new Issue(1, ORIGINAL_TITLE, ORIGINAL_CONTENT);
        writer = new User("writer", "password", "name");
        issue.setWriter(writer);
    }

//    @Test
//    public void getStatus_Open() {
//        assertEquals("#1 Open", issue.getStatus());
//    }
//
//    @Test
//    public void getStatus_Closed() {
//        issue.setStatus(IssueStatus.CLOSED);
//        assertEquals("#1 Closed", issue.getStatus());
//    }

    @Test
    public void update_Success() {
        issue.update(writer, new Issue("title", "updated content"));
        assertThat(issue.getContent(), is("updated content"));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_Writer_LoginUser_Mismatch() {
        User notWriter = new User("notWriter", "password", "name");
        issue.update(notWriter, new Issue("title", "updated content"));
        assertThat(issue.getContent(), is(ORIGINAL_CONTENT));
    }

    @Test
    public void delete_Success() {
        issue.delete(writer);
        assertThat(issue.isDeleted(), is(true));
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_Writer_LoginUser_Mismatch() {
        User notWriter = new User("notWriter", "password", "name");
        issue.delete(notWriter);
        assertThat(issue.isDeleted(), is(false));
    }

    @Test
    public void setAssignee_Success() {
        User loginUser = writer;
        User assignee = new User("assignee", "password", "name");
        issue.setAssignee(loginUser, assignee);
        assertTrue(issue.isAssignee(assignee));
    }

    @Test(expected = UnAuthorizedException.class)
    public void setAssignee_Fail_LoginUser_And_Writer_Mismatch() {
        User loginUser = new User("notWriter", "password", "name");
        User assignee = new User("assignee", "password", "name");
        issue.setAssignee(loginUser, assignee);
        assertFalse(issue.isAssignee(assignee));
    }

    @Test
    public void setLabel_Success() {
        User loginUser = writer;
        issue.setLabel(loginUser, 1L);
        assertTrue(issue.isLabel(Label.JAVA));
    }

    @Test(expected = UnAuthorizedException.class)
    public void setLabel_Fail_LoginUser_And_Writer_Mistmatch() {
        User loginUser = new User("notWriter", "password", "name");
        issue.setLabel(loginUser, 1L);
        assertFalse(issue.isLabel(Label.JAVA));
    }
}
