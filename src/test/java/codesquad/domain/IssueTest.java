package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest extends BaseTest {
    public static Issue issue1;

    @Before
    public void setUp() {
        issue1 = new Issue(JAVAJIGI,new IssueBody("제목입니다.","내용 입니다."));;
    }


    @Test
    public void issueBodyTest() {
        issue1.setSubject("제목 수정");
        softly.assertThat(issue1.getSubject()).isEqualTo("제목 수정");
        issue1.setComment("내용 수정했다.");
        softly.assertThat(issue1.getComment()).isEqualTo("내용 수정했다.");
    }

    @Test
    public void update() {
        IssueBody issueBody = new IssueBody("수정됬습니다.","내용도 수정됬습니다.");
        issue1.update(JAVAJIGI,issueBody);
        softly.assertThat(issue1.getComment()).isEqualTo("내용도 수정됬습니다.");
        softly.assertThat(issue1.getSubject()).isEqualTo("수정됬습니다.");

    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not() {
        IssueBody issueBody = new IssueBody("수정됬습니다.","내용도 수정됬습니다.");
        issue1.update(SANJIGI,issueBody);
    }


    @Test
    public void delete() {
        softly.assertThat(issue1.isDeleted()).isTrue();
        issue1.deleted(JAVAJIGI);
        softly.assertThat(issue1.isDeleted()).isFalse();
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no() {
        softly.assertThat(issue1.isDeleted()).isTrue();
        issue1.deleted(SANJIGI);
        softly.assertThat(issue1.isDeleted()).isTrue();
    }
}
