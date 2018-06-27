package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IssueTest {
    @Test
    public void isOwnerTest() {
        Issue issue = new Issue("subject", "comment");
        User user = new User("testuser", "testpassword", "testname");
        issue.writeBy(user);
        assertTrue(issue.isOwner(user));
    }

    @Test
    public void update() {
        Issue origin = new Issue("subject", "comment");
        User loginUser = new User("testuser", "testpassword", "testname");
        origin.writeBy(loginUser);

        Issue target = new Issue("update subject", "update comment");
        target.writeBy(loginUser);

        origin.update(loginUser, target);
        assertTrue(origin.equals(target));
    }
}
