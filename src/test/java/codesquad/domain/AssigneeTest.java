package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AssigneeTest {
    @Test
    public void create() {
        User user = new User(1L, "learner", "password", "황러너");
        Issue issue = new Issue(2L, "JPA 이슈", "JPA 코멘트", user);
        Assignee assignee = new Assignee(user.getId(), issue.getId());
        assertThat(assignee.toString().contains("1"), is(true));
        assertThat(assignee.toString().contains("2"), is(true));
    }
}
