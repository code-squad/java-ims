package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class IssueTest extends BaseTest {
    public static final List<Issue> ISSUES = new ArrayList<>();
    public static final Issue ISSUE1 = new Issue("testSubject1", "testComment1");
    public static final Issue ISSUE2 = new Issue("testSubject2", "testComment2");

    static {
        ISSUES.add(ISSUE1);
        ISSUES.add(ISSUE2);
    }

    @Test
    public void create() {
        softly.assertThat(ISSUE1.getSubject()).isEqualTo("testSubject1");
        softly.assertThat(ISSUE1.getComment()).isEqualTo("testComment1");
    }
}
