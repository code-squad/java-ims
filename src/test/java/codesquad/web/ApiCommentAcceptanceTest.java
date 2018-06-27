package codesquad.web;

import codesquad.domain.Comment;
import codesquad.dto.CommentDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiCommentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentAcceptanceTest.class);

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

        basicAuthTemplate( ).put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("modified comment"));
    }

    @Test
    public void update_fail_unAuthentication() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();

        template( ).put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment"));
    }

    @Test
    public void update_fail_unAuthorization() {
        Comment createdComment = requestPost(basicAuthTemplate(), "/api/issues/1/comments", getComment(true), Comment.class).getBody();

        basicAuthTemplate(findByUserId("sanjigi")).put("/api/issues/1/comments/" + createdComment.getId(), getUpdateComment());
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/1");
        assertTrue(response.getBody().contains("test comment"));
    }

    private CommentDto getComment(boolean isValid) {
        if (isValid) {
            return new CommentDto("test comment");
        }
        return new CommentDto("aa");
    }

    private CommentDto getUpdateComment() {
        return new CommentDto("modified comment");
    }
}