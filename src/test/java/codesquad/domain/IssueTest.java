package codesquad.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class IssueTest {
    public static final Issue issue1 = new Issue(1L, "First Issue", "First Comment");
    public static final Issue issue2 = new Issue(2L, "Second Issue", "Second Comment");

    @Test
    public void create() {
        Issue issue = new Issue("create subject", "comment");
        String result = "create subject";
        assertThat(issue.getSubject(), is(result));
    }
}
