package codesquad.domain;

import org.junit.Test;
import support.test.AcceptanceTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class IssueTest extends AcceptanceTest {
    User loginUser = findDefaultUser();

    @Test
    public void create() {
        Issue issue = new Issue(1L, "first Issue", "first comment", loginUser);
        String result = "create subject";
        assertThat(issue.getSubject(), is(result));
    }
}
