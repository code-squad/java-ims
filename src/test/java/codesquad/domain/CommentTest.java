package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.issue.Comment;
import org.junit.Test;

import static codesquad.domain.IssueTest.ISSUE_JAVAJIGI;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommentTest {
    public static final Comment COMMENT_JAVAJIGI = new Comment(JAVAJIGI, ISSUE_JAVAJIGI, "comment by javajigi");

    @Test
    public void match_writer() throws Exception {
        assertTrue(COMMENT_JAVAJIGI.isMatchWriter(JAVAJIGI));
    }

    @Test
    public void mismatch_writer() throws Exception {
        assertFalse(COMMENT_JAVAJIGI.isMatchWriter(SANJIGI));
    }

    @Test
    public void update_owner() throws Exception {
        Comment origin = COMMENT_JAVAJIGI;
        String target = "updated comment by javajigi";

        origin.update(JAVAJIGI, target);
        assertThat(origin.getComment(), is(target));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        Comment origin = COMMENT_JAVAJIGI;
        String target = "updated comment by javajigi";

        origin.update(SANJIGI, target);
    }

    @Test
    public void delete_삭제가능_작성자의이슈() throws Exception {
        Comment origin = COMMENT_JAVAJIGI;
        origin.delete(JAVAJIGI);
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_삭제불가_타인의이슈() throws Exception {
        Comment origin = COMMENT_JAVAJIGI;
        origin.delete(SANJIGI);
    }
}
