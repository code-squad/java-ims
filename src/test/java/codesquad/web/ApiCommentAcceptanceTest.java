package codesquad.web;

import codesquad.domain.Comment;
import codesquad.dto.CommentDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

    private CommentDto getComment(boolean isValid) {
        if (isValid) {
            return new CommentDto("test comment");
        }
        return new CommentDto("aa");
    }
}