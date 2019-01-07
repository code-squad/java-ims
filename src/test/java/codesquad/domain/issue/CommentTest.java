package codesquad.domain.issue;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.UserTest.JUNGHYUN;

public class CommentTest extends BaseTest {
    public static long RANDOM_COMMENT_ID = 100L;
    public static final String CONTENTS = "답변내용입니다1";
    public static final Comment COMMENT = new Comment(1L, CONTENTS, ISSUE, BRAD);
    public static final String CONTENTS2 = "답변내용입니다2";
    public static final Comment COMMENT2 = new Comment(2L, CONTENTS2, ISSUE, BRAD);
    public static final String CONTENTS3 = "답변내용입니다3";
    public static final Comment COMMENT3 = new Comment(3L, CONTENTS3, ISSUE, BRAD);
    public static final String NEW_CONTENTS = "새로운 답변내용입니다";
    public static final String UPDATE_CONTENTS = "새로운 답변내용입니다";
    public static final List<Comment> COMMENTS = new ArrayList<>();

    static {
        COMMENTS.add(COMMENT);
        COMMENTS.add(COMMENT2);
        COMMENTS.add(COMMENT3);
    }

    @Test
    public void delete() {
        COMMENT.delete(BRAD, ISSUE);
        softly.assertThat(COMMENT.isDeleted()).isTrue();
    }

    @Test
    public void update() {
        Comment comment = new Comment(RANDOM_COMMENT_ID, NEW_CONTENTS, ISSUE, BRAD);
        Comment updatedCommnet = comment.update(BRAD, ISSUE, new Comment(UPDATE_CONTENTS));
        softly.assertThat(updatedCommnet.getContents()).isEqualTo(UPDATE_CONTENTS);
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_다른유저일때() {
        Comment comment = new Comment(RANDOM_COMMENT_ID, NEW_CONTENTS, ISSUE, BRAD);
        Comment updatedCommnet = comment.update(JUNGHYUN, ISSUE, new Comment(UPDATE_CONTENTS));
    }

    @Test
    public void isOwner() {
        softly.assertThat(COMMENT.isOwner(BRAD)).isTrue();
    }
}