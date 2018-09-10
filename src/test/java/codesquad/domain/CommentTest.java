package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommentTest {
    final static private User writer = new User(1L, "javajigi", "password", "ParkJaesung");
    final static private String default_contents = "이슈 댓글 내용";

    @Test
    public void create() {
        Comment comment = new Comment(writer, default_contents);
        assertThat(comment.toString().contains("이슈 댓글 내용"), is(true));
    }

    @Test
    public void writtenby() {
        Comment comment = new Comment(default_contents);
        comment.writtenby(writer);
        User savedWriter = comment.getWriter();

        assertThat(savedWriter.equals(writer), is(true));
    }

    @Test
    public void generatedUri() {
        Long issueId = 2L;
        Comment comment = new Comment(3L, writer, "댓글에는 문제가 없습니다.");
        assertThat(comment.generatedUri(issueId), is("/api/issues/" + issueId + "/comments/3"));
    }

    @Test
    public void update() {
        Comment comment = new Comment(writer, default_contents);
        Comment updatedComment = new Comment(writer, "수정된 댓글 내용");

        comment.update(updatedComment);
        assertThat(comment.toString().contains("수정된 댓글 내용"), is(true));
    }

    @Test
    public void isDeleted() {
        Comment comment = new Comment(writer, default_contents);
        assertThat(comment.isDeleted(), is(false));
    }

    @Test
    public void delete() {
        Comment comment = new Comment(writer, default_contents);
        comment.delete(writer);
        assertThat(comment.isDeleted(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_fail() {
        User not_writer = new User(2L, "learner", "password", "taewon");
        Comment comment = new Comment(writer, default_contents);
        comment.delete(not_writer);
        assertThat(comment.isDeleted(), is(false));
    }
}
