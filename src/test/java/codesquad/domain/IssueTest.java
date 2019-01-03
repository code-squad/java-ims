package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest extends BaseTest {

    @Test
    public void issue_create() {
        Issue issue = new Issue("안녕하세요~~", "하하하", new User());
        softly.assertThat(issue.getContents().getSubject()).isEqualTo("안녕하세요~~");
        softly.assertThat(issue.getContents().getComment()).isEqualTo("하하하");
    }
}
