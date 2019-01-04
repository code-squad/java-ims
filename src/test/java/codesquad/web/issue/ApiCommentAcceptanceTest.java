package codesquad.web.issue;

import org.junit.Test;
import support.test.AcceptanceTest;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.issue.CommentTest.COMMENT;

public class ApiCommentAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        String url = String.format("/api/issues/%d/answers", ISSUE.getId());
        String location = createResource(url, BRAD, COMMENT);
    }
}