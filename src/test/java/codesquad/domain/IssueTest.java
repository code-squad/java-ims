package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;

public class IssueTest extends BaseTest {
    public static final Issue ISSUE_NO1 = new Issue(JAVAJIGI,new IssueBody("제목입니다.","내용 입니다."));

    @Test
    public void issueBodyTest() {
        Issue issue = ISSUE_NO1;
        issue.setSubject("제목 수정");
        softly.assertThat(issue.getSubject()).isEqualTo("제목 수정");
        issue.setComment("내용 수정했다.");
        softly.assertThat(issue.getComment()).isEqualTo("내용 수정했다.");
    }


}
