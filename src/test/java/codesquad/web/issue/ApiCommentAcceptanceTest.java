package codesquad.web.issue;

import codesquad.domain.issue.Comment;
import codesquad.domain.issue.IssueRepository;
import codesquad.validate.ValidationErrorsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.*;
import static support.test.ValidationTest.VALIDATOR;

public class ApiCommentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiCommentAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void create() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        Comment comment = basicAuthTemplate().getForObject(location, Comment.class);
        softly.assertThat(comment.getContents()).isEqualTo(CONTENTS);
        softly.assertThat(comment.getWriter()).isEqualTo(BRAD);
        log.debug("comment : {}", comment);
    }

    @Test
    public void create_invalid() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        Comment comment = new Comment("a");
        ResponseEntity<ValidationErrorsResponse> response = basicAuthTemplate().postForEntity(url, comment, ValidationErrorsResponse.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Set<ConstraintViolation<Comment>> violations = VALIDATOR.validate(comment);
        softly.assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void delete() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        ResponseEntity<Void> response = basicAuthTemplate().exchange(location, HttpMethod.DELETE, createHttpEntity(null), Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        ResponseEntity<Comment> response = basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(new Comment(UPDATE_CONTENTS)), Comment.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("updateComment : {}", response.getBody());
    }

    @Test
    public void updateRequest() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        ResponseEntity<Comment> response = basicAuthTemplate().getForEntity(location, Comment.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("updateRequest Comment : {}", response.getBody());
    }

    @Test
    public void updateRequest_no_login() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        ResponseEntity<Comment> response = template().getForEntity(location, Comment.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void updateRequest_other_login() {
        String url = String.format("/api/issues/%d/comments", ISSUE.getId());
        String location = createResource(url, BRAD, new Comment(CONTENTS));
        ResponseEntity<Comment> response = basicAuthTemplate(JUNGHYUN).getForEntity(location, Comment.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}