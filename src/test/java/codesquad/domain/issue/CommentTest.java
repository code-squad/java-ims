package codesquad.domain.issue;

import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;

public class CommentTest extends BaseTest {
    public static final String CONTENTS = "답변내용입니다1";
    public static final Comment COMMENT = new Comment(1L, CONTENTS, ISSUE, BRAD);
    public static final String CONTENTS2 = "답변내용입니다2";
    public static final Comment COMMENT2 = new Comment(2L, CONTENTS2, ISSUE, BRAD);
    public static final String CONTENTS3 = "답변내용입니다3";
    public static final Comment COMMENT3 = new Comment(3L, CONTENTS3, ISSUE, BRAD);

    @Test
    public void delete() {
        Comment comment = COMMENT.delete(BRAD, ISSUE);
        softly.assertThat(comment.isDeleted()).isTrue();
    }
}