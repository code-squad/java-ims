package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueTest {

    @Test
    public void create() {
        Issue issue = new Issue("subject", "comment");
        assertThat(issue.toString().contains("subject")
                &&issue.toString().contains("comment"),is(true));
    }

    @Test
    public void update() {
        User user = new User("learner", "test1234", "taewon");
        Issue issue = new Issue("subject", "comment");
        issue.writeBy(user);

        Issue newIssue = new Issue("updateSubject", "updateComment");
        newIssue.writeBy(user);

        issue.update(newIssue);
        assertThat(issue.toString().contains("updateSubject"), is(true));

    }
}
