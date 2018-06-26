package codesquad.domain;

import codesquad.CannotDeleteException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.AcceptanceTest;

import javax.swing.plaf.PanelUI;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class IssueTest {
    private static final Logger log =  LoggerFactory.getLogger(IssueTest.class);
    private User defaultUser;
    private User jimmyUser;
    private Issue issue;

    @Before
    public void setUp() {
        defaultUser = new User(1L,"javajigi", "123456", "자바지기");
        jimmyUser = new User(2L,"jimmy", "123456", "김재연");
        issue = new Issue("before issue", "before comment", defaultUser);
    }

    @Test
    public void create() {
        Issue issue = new Issue(1L, "first Issue", "first comment", defaultUser);
        String result = "first Issue";
        log.info(issue.toString());
        assertThat(issue.getSubject(), is(result));
    }

    @Test
    public void isOwner() {
        boolean result = issue.isOwner(jimmyUser);
        assertThat(result, is(false));
    }

    @Test
    public void update() throws Exception {
        Issue target = new Issue("update", "updated", defaultUser);
        issue.update(defaultUser, target);
    }

    @Test(expected = CannotDeleteException.class)
    public void update_fail() throws Exception {
        Issue target = new Issue("update", "updated", defaultUser);
        issue.update(jimmyUser, target);
    }

    @Test
    public void update_assignee() throws Exception {
        issue.updateAssignee(defaultUser, jimmyUser);
        assertThat(issue.getAssignee(), is(jimmyUser));
    }

    @Test
    public void update_assignee_fail() throws Exception {
        issue.updateAssignee(jimmyUser, jimmyUser);
        assertThat(issue.getAssignee(), is(jimmyUser));
    }

    @Test
    public void update_label() throws Exception {
        issue.updateLabel(defaultUser, Label.Planning);
        assertThat(issue.getLabel(), is(Label.Planning));
    }

    @Test
    public void update_label_fail() throws Exception {
        issue.updateLabel(jimmyUser, Label.Planning);
        assertThat(issue.getLabel(), is(Label.Planning));
    }
}
