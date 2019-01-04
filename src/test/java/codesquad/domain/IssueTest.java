package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import support.test.BaseTest;

public class IssueTest extends BaseTest {

    public static Issue issue1 = new Issue("테스트제목1", "테스트내용1", UserTest.JAVAJIGI);
    public static Issue issue2 = new Issue("테스트제목2", "테스트내용2", UserTest.SANJIGI);

    @Test
    public void create() {
        Issue issue = new Issue("안녕하세요~~", "하하하", new User());
        softly.assertThat(issue.getContents().getSubject()).isEqualTo("안녕하세요~~");
        softly.assertThat(issue.getContents().getComment()).isEqualTo("하하하");
    }

    @Test
    public void update_same_user() {
        Issue updateIssue = new Issue("변경된제목1", "변경된내용1");
        issue1.update(UserTest.JAVAJIGI, updateIssue.getContents());

        softly.assertThat(issue1.getContents().getSubject()).isEqualTo("변경된제목1");
        softly.assertThat(issue1.getContents().getComment()).isEqualTo("변경된내용1");
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_other_user() {
        Issue updateIssue = new Issue("변경된제목1", "변경된내용1");
        issue1.update(UserTest.SANJIGI, updateIssue.getContents());
    }

    @Test
    public void delete_same_user() {
        softly.assertThat(issue1.delete(UserTest.JAVAJIGI)).isNotNull();
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_other_user() {
        softly.assertThat(issue1.delete(UserTest.SANJIGI));
    }
}
