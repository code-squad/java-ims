package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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

    @Test
    public void generatedUri() {
        User writer = new User(1L, "javajigi", "password", "jaesung");
        Comment comment = new Comment(3L, writer, "댓글에는 문제가 없습니다.");
        Long issueId = 2L;
        assertThat(comment.generatedUri(issueId), is("/api/issues/2/comments/3"));
    }
}
