package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommentTest {
    @Test
    public void create() {
        User writer = new User(1L, "javajigi", "password", "park");
        String contents = "이슈 댓글 내용";
        Comment comment = new Comment(writer, contents);

        assertThat(comment.toString().contains("이슈 댓글 내용"), is(true));
    }

    @Test
    public void writtenby() {
        User writer = new User(1L, "javajigi", "password", "jaesung");
        String contents = "이슈 댓글 내용";
        Comment comment = new Comment(contents);
        comment.writtenby(writer);
        User savedWriter = comment.getWriter();
        assertThat(savedWriter.equals(writer), is(true));
    }
}
