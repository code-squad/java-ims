package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.domain.issue.Comment;
import org.junit.Test;
import support.test.BaseTest;

import static support.test.Fixture.*;

public class CommentTest extends BaseTest {

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