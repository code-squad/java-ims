package codesquad.web;

import codesquad.domain.Comment;
import codesquad.dto.CommentDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiCommentAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        ResponseEntity<Comment> response = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertNotNull(response.getBody());
    }

    @Test
    public void create_fail_UnAuthentication() {
        ResponseEntity<Comment> response = requestPost(template(), "/api/issues/1/comments", getComment(true), Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void create_fail_invalidIssueId() {
        ResponseEntity<Comment> response = requestPost(basicAuthTemplate(), "/api/issues/100/comments", getComment(true), Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void create_fail_invalidCommentParams() {
        ResponseEntity<Comment> response = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(false), Comment.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void update() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();

        basicAuthTemplate().put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("modified comment"));
    }

    @Test
    public void update_fail_unAuthentication() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();

        template().put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment data"));
    }

    @Test
    public void update_fail_unAuthorization() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();

        basicAuthTemplate(findByUserId("sanjigi")).put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment data"));
    }

    @Test
    public void delete() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();
        basicAuthTemplate().delete("/api/issues/1/comments/" + createdComment.getId());

        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertFalse(response.getBody().contains("test comment data"));
    }

    @Test
    public void delete_fail_unAuthentication() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();
        template().delete("/api/issues/1/comments/" + createdComment.getId());

        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment data"));
    }

    @Test
    public void delete_fail_unAuthorization() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();
        basicAuthTemplate(findByUserId("sanjigi")).delete("/api/issues/1/comments/" + createdComment.getId());

        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment data"));
    }

    private CommentDto getComment(boolean isValid) {
        if (isValid) {
            return new CommentDto("test comment data");
        }
        return new CommentDto("aa");
    }

    private CommentDto getUpdateComment() {
        return new CommentDto("modified comment");
    }
}