package codesquad.domain;

import org.junit.Test;
import support.test.AcceptanceTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class IssueTest extends AcceptanceTest {
    User loginUser = findDefaultUser();
//    public static final Issue issue1 = new Issue(1L, "First Issue", "First Comment", loginUser);
//    public static final Issue issue2 = new Issue(2L, "Second Issue", "Second Comment");
    Issue issue = new Issue(1L, "first Issue", "first comment", loginUser);
    @Test
    public void create() {
        Issue issue = new Issue("create subject", "comment");
        String result = "create subject";
        assertThat(issue.getSubject(), is(result));
    }
}
