package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class DeleteHistoryTest extends BaseTest {

    @Test
    public void issueDelete() {
        Issue issue1 = new Issue(JAVAJIGI, new ContentsBody("제목입니다.", "내용 입니다."));
        DeleteHistory deleteHistory = issue1.deleted(JAVAJIGI);
        softly.assertThat(deleteHistory.toString()).contains("userId=javajigi");
    }


    @Test(expected = UnAuthorizedException.class)
    public void issueDelete_no() {
        Issue issue1 = new Issue(SANJIGI, new ContentsBody("제목입니다.", "내용 입니다."));
        DeleteHistory deleteHistory = issue1.deleted(JAVAJIGI);
    }
}
