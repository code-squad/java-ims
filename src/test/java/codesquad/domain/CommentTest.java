package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import codesquad.domain.issue.Comment;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class CommentTest extends BaseTest {
    public static final Comment JAVAJIGI_COMMENT = new Comment("originalComment", JAVAJIGI);
    public static final Comment SANJIGI_COMMENT = new Comment("updatedComment", SANJIGI);
    public static final Comment UPDATED_JAVAJIGI_COMMENT = new Comment("updatedComment", JAVAJIGI);
    public static final Comment UPDATED_SANJIGI_COMMENT = new Comment("updatedComment", SANJIGI);


    @Test
    public void update_success() {
        JAVAJIGI_COMMENT.update(JAVAJIGI, UPDATED_JAVAJIGI_COMMENT);
        softly.assertThat(JAVAJIGI_COMMENT.getContents()).isEqualTo(UPDATED_JAVAJIGI_COMMENT.getContents());
    }

    @Test(expected = CannotUpdateException.class)
    public void update_fail() {
        JAVAJIGI_COMMENT.update(SANJIGI, UPDATED_SANJIGI_COMMENT);
    }

    @Test
    public void delete_success() {
        JAVAJIGI_COMMENT.delete(JAVAJIGI);
        softly.assertThat(JAVAJIGI_COMMENT.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() {
        JAVAJIGI_COMMENT.delete(SANJIGI);
    }
}
