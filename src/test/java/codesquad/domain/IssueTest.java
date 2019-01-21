package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest extends BaseTest {
    public Issue issue1;

    @Before
    public void setUp() {
        issue1 = new Issue(JAVAJIGI,new ContentsBody("제목입니다.","내용 입니다."));
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
        ContentsBody contentsBody = new ContentsBody("수정됬습니다.","내용도 수정됬습니다.");
        issue1.update(JAVAJIGI, contentsBody);
        softly.assertThat(issue1.getComment()).isEqualTo("내용도 수정됬습니다.");
        softly.assertThat(issue1.getSubject()).isEqualTo("수정됬습니다.");

    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not() {
        ContentsBody contentsBody = new ContentsBody("수정됬습니다.","내용도 수정됬습니다.");
        issue1.update(SANJIGI, contentsBody);
    }


    @Test
    public void delete() {
        softly.assertThat(issue1.isDeleted()).isFalse();
        issue1.deleted(JAVAJIGI);
        softly.assertThat(issue1.isDeleted()).isTrue();
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no() {
        softly.assertThat(issue1.isDeleted()).isFalse();
        issue1.deleted(SANJIGI);
        softly.assertThat(issue1.isDeleted()).isFalse();
    }

    @Test
    public void addAnswer() {
        Answer answer = new Answer(JAVAJIGI,"하하하하");
        issue1.addAnswer(answer);
        softly.assertThat(answer.getUserId()).isEqualTo(JAVAJIGI.getUserId());
        softly.assertThat(issue1.getAnswers()).isNotEmpty();
    }
}
