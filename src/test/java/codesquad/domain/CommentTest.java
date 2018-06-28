package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommentTest {

    private Comment comment;

    @Before
    public void setup(){
        comment = new Comment("testComment");
        comment.writeBy(UserTest.JAVAJIGI);
    }

    @Test
    public void update() {
        Comment target = new Comment("testComment2");
        target.writeBy(UserTest.JAVAJIGI);

        Comment update = comment.update(UserTest.JAVAJIGI, target);
        assertThat(update.getComment(), is("testComment2"));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_no_owner() {
        Comment target = new Comment("testComment2");
        target.writeBy(UserTest.JAVAJIGI);

        Comment update = comment.update(UserTest.SANJIGI, target);
    }

    @Test
    public void delete() {
        DeleteHistory deleteHistory = comment.delete(UserTest.JAVAJIGI);
        assertThat(deleteHistory, is(new DeleteHistory(0L, ContentType.COMMENT, UserTest.JAVAJIGI)));
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_no_owner() {
        DeleteHistory deleteHistory = comment.delete(UserTest.SANJIGI);
    }
}
