package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

public class IssueTest extends BaseTest {
    public static final Issue FIRST_ISSUE = new Issue(1L, "First issue test", "first comment");
    public static final Issue SECOND_ISSUE = new Issue(2L, "Second issue test", "Second comment");

    @Test
    public void create() {
        Issue issue = new Issue(1L,"test issue", "test comment");
        softly.assertThat(issue.getId()).isEqualTo(1);
        softly.assertThat(issue.getSubject()).isEqualTo("test issue");
        softly.assertThat(issue.getComment()).isEqualTo("test comment");
    }
}
