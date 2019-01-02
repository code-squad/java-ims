package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class IssueTest extends BaseTest {
    public static final List<Issue> ISSUES = new ArrayList<>();
    public static final Issue ISSUE1 = new Issue(1L,"testSubject1", "testComment1");
    public static final Issue ISSUE2 = new Issue(2L, "testSubject2", "testComment2");
    public static final Issue ISSUE3 = new Issue(3L, "testSubject3", "testComment3");

    static {
        ISSUES.add(ISSUE1);
        ISSUES.add(ISSUE2);
        ISSUES.add(ISSUE3);
    }

    @Test
    public void create() {
        softly.assertThat(ISSUE1.getSubject()).isEqualTo("testSubject1");
        softly.assertThat(ISSUE1.getComment()).isEqualTo("testComment1");
    }
}
