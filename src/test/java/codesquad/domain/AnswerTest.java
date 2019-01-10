package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static support.test.Fixtures.ISSUE_NO1;

public class AnswerTest extends BaseTest {
    private Answer answer;

    @Before
    @Test
    public void create() {
        answer = new Answer(JAVAJIGI, "내용입니다.");
        answer.toIssue(ISSUE_NO1);
        softly.assertThat(answer.getUserId()).isEqualTo(JAVAJIGI.getUserId());
        softly.assertThat(answer.getComment()).isEqualTo("내용입니다.");
    }

    @Test
    public void toIssue() {
        softly.assertThat(answer.isOwner(JAVAJIGI)).isTrue();
        softly.assertThat(answer.isOwner(SANJIGI)).isFalse();
    }

    @Test
    public void update() {
        //
        softly.assertThat(answer.getComment()).isEqualTo("내용입니다.");
        answer.update(JAVAJIGI, "수정합니다.");
        softly.assertThat(answer.getComment()).isEqualTo("수정합니다.");
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_no() {
        answer.update(SANJIGI, "수정합니다.");
    }

    @Test
    public void delete() {
        //DeleteHistory 생성
        softly.assertThat(answer.isDeleted()).isFalse();
        DeleteHistory deleteHistory = answer.deleted(JAVAJIGI);
        softly.assertThat(answer.isDeleted()).isTrue();
        softly.assertThat(deleteHistory.toString()).contains("userId=javajigi");
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no() {
        answer.deleted(SANJIGI);
    }


}
