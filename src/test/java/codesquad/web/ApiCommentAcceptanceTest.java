package codesquad.web;

import codesquad.domain.Comment;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiCommentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentAcceptanceTest.class);

    public ResponseEntity<Comment> createComment() {
        Comment comment = new Comment(1L, "댓글 문제가 아니야");
        comment.writtenby(findDefaultUser());
        return basicAuthTemplate().postForEntity("/api/issues/1/comments", comment, Comment.class);
    }

    @Test
    public void create() {
        Comment comment = new Comment(1L, "댓글 문제가 아니야");
        comment.writtenby(findDefaultUser());
        ResponseEntity<Comment> response = basicAuthTemplate().postForEntity("/api/issues/1/comments", comment, Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void getComment() {
        Long issueId = 1L;
        ResponseEntity<Comment> response = createComment();
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        Comment responseComment = response.getBody();
        response = basicAuthTemplate().getForEntity(responseComment.generatedUri(issueId), Comment.class);
        responseComment = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseComment.toString().contains("댓글 문제가 아니야"), is(true));
    }


    @Test
    public void update() {
        Long issueId = 1L;
        ResponseEntity<Comment> response = createComment();
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Comment comment = response.getBody();

        Comment savedComment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        Comment updatedComment = new Comment("수정된 댓글 문제가 아닙니다.");
        updatedComment.writtenby(findDefaultUser());
        basicAuthTemplate().put(savedComment.generatedUri(issueId), updatedComment);

        savedComment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(savedComment.toString().contains("수정된 댓글 문제가 아닙니다."), is(true));
    }

    @Test
    public void delete() {
        Long issueId = 1L;
        ResponseEntity<Comment> response = createComment();
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Comment comment = response.getBody();

        comment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(comment.isDeleted(), is(false));

        basicAuthTemplate().delete("/api/issues/" + issueId + "/comments/" + comment.getId());

        comment = basicAuthTemplate().getForObject(comment.generatedUri(issueId), Comment.class);
        assertThat(comment.isDeleted(), is(true));
    }
}
