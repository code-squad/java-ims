package codesquad.domain;

import codesquad.CannotDeleteException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommentsTest {

    private List<Comment> comments;

    @Before
    public void setup() {
        Comment comment = new Comment("testComment");
        comment.writeBy(UserTest.JAVAJIGI);

        Comment comment2 = new Comment("testComment2");
        comment2.writeBy(UserTest.JAVAJIGI);

        comments = Arrays.asList(comment, comment2);
    }

    @Test
    public void delete(){
        Comments commentsTest = new Comments(comments);
        List<DeleteHistory> deleteHistories = commentsTest.delete(UserTest.JAVAJIGI);
        assertThat(deleteHistories.size(), is(2));

        assertThat(commentsTest.getComments().get(0).isDeleted(), is(true));
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_다른_사용자(){
        Comments commentsTest = new Comments(comments);
        List<DeleteHistory> deleteHistories = commentsTest.delete(UserTest.SANJIGI);
    }
}
