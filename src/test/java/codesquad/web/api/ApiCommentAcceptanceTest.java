package codesquad.web.api;

import codesquad.domain.issue.Comment;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.CommentTest.JAVAJIGI_COMMENT;
import static codesquad.domain.CommentTest.UPDATED_JAVAJIGI_COMMENT;
import static codesquad.domain.UserTest.SANJIGI;

public class ApiCommentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentAcceptanceTest.class);

    private String location;

    @Before
    public void setUp() throws Exception {
        location = createResource("/api/issues/1/comments", JAVAJIGI_COMMENT) + "/comments/1";
    }

    @Test
    public void create_no_login() {
        ResponseEntity<Void> responseEntity =
                template.postForEntity("/api/issues/1/comments", JAVAJIGI_COMMENT, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void create_login() {
        ResponseEntity<Void> responseEntity =
                basicAuthTemplate().postForEntity("/api/issues/1/comments", JAVAJIGI_COMMENT, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void update_no_login() {
        ResponseEntity<Comment> responseEntity =
                template.exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_JAVAJIGI_COMMENT), Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_login() {
        ResponseEntity<Comment> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_JAVAJIGI_COMMENT), Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_other_user() {
        ResponseEntity<Comment> responseEntity =
                basicAuthTemplate(SANJIGI).exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_JAVAJIGI_COMMENT), Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_no_login() {
        ResponseEntity<Comment> responseEntity =
                template.exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_login() {
        ResponseEntity<Comment> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void delete_other_user() {
        ResponseEntity<Comment> responseEntity =
                basicAuthTemplate(SANJIGI).exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Comment.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
