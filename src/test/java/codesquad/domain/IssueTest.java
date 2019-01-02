package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

public class IssueTest extends BaseTest {
    public static final Issue ISSUE1 = new Issue("testSubject1", "testComment1");
    public static final Issue ISSUE2 = new Issue("testSubject2", "testComment2");

    @Test
    public void create() {
        softly.assertThat(ISSUE1.getSubject()).isEqualTo("testSubject1");
        softly.assertThat(ISSUE1.getComment()).isEqualTo("testComment1");
    }
}
