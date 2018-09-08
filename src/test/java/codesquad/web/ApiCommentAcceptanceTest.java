package codesquad.web;

import codesquad.domain.Comment;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiCommentAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        Comment comment = new Comment(1L, findDefaultUser(), "댓글 문제가 아니야");
        ResponseEntity<Comment> response = basicAuthTemplate().postForEntity("/api/issues/1/comments", comment, Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void getComment() {
        Long issueId = 1L;
        Comment comment = new Comment(1L, findDefaultUser(), "댓글 문제가 아닙니다.");
        ResponseEntity<Comment> response = basicAuthTemplate().postForEntity("/api/issues/" + issueId + "/comments", comment, Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        Comment dbComment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(dbComment.toString().contains("댓글 문제가 아닙니다."), is(true));
    }


    @Test
    public void update() {
        Long issueId = 1L;
        Comment comment = new Comment(1L, findDefaultUser(), "댓글 문제가 아닙니다.");
        ResponseEntity<Void> response = basicAuthTemplate().postForEntity("/api/issues/" + issueId + "/comments", comment, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        Comment savedComment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        Comment updatedComment = new Comment(findDefaultUser(), "수정된 댓글 문제가 아닙니다.");
        basicAuthTemplate().put(savedComment.generatedUri(issueId), updatedComment);

        savedComment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(savedComment.toString().contains("수정된 댓글 문제가 아닙니다."), is(true));
    }

    @Test
    public void delete() {
        Long issueId = 1L;
        Comment comment = new Comment(1L, findDefaultUser(), "댓글 문제가 아닙니다.");
        ResponseEntity<Void> response = basicAuthTemplate().postForEntity("/api/issues/" + issueId + "/comments", comment, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        comment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(comment.isDeleted(), is(false));

        basicAuthTemplate().delete("/api/issues/" + issueId + "/comments/" + comment.getId());

        comment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(comment.isDeleted(), is(true));
    }
}
