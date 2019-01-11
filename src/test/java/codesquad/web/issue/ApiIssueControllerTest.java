package codesquad.web.issue;

import codesquad.domain.issue.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.domain.ErrorMessage;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.*;

public class ApiIssueControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueControllerTest.class);

    @Test
    public void show() {
        String location = createResource("/api/issues", BRAD, ISSUE_BODY);
        ResponseEntity<Issue> responseEntity = template().getForEntity(location, Issue.class);
        softly.assertThat(responseEntity.getBody().hasSameBody(ISSUE_BODY)).isEqualTo(true);
        softly.assertThat(responseEntity.getBody().isOwner(BRAD)).isEqualTo(true);
    }

    @Test
    public void create_로그인안한유저() {
        ResponseEntity<String> response = template().postForEntity("/api/issues", ISSUE, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        log.debug("errorMessage : {}", response.getBody());
    }

    @Test
    public void update() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<Issue> responseEntity = basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_ISSUE_BODY), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getBody().hasSameBody(UPDATE_ISSUE_BODY)).isTrue();
    }

    @Test
    public void update_로그인안한유저() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<ErrorMessage> responseEntity = template().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_ISSUE_BODY), ErrorMessage.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        log.debug("errorMessage : {}", responseEntity.getBody().getMessage());
    }

    @Test
    public void update_다른유저() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<ErrorMessage> responseEntity = basicAuthTemplate(JUNGHYUN).exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_ISSUE_BODY), ErrorMessage.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<Void> responseEntity = basicAuthTemplate().exchange(location, HttpMethod.DELETE, null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void delete_로그인안한유저() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<Void> responseEntity = template().exchange(location, HttpMethod.DELETE, null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_다른유저() {
        String location = createResource("/api/issues", BRAD, NEW_ISSUE_BODY);
        ResponseEntity<Void> responseEntity = basicAuthTemplate(JUNGHYUN).exchange(location, HttpMethod.DELETE, null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void setMilestone() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/api/issues/%d/milestones/%d", ISSUE.getId(), MILESTONE.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void setMilestone_Issue_작성자_아닐때() {
        ResponseEntity<String> response = basicAuthTemplate(JUNGHYUN).getForEntity(String.format("/api/issues/%d/milestones/%d", ISSUE.getId(), MILESTONE.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void setAssignee() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/api/issues/%d/assignees/%d", ISSUE.getId(), BRAD.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void setAssignee_Issue_작성자_아닐때() {
        ResponseEntity<String> response = basicAuthTemplate(JUNGHYUN).getForEntity(String.format("/api/issues/%d/milestones/%d", ISSUE.getId(), MILESTONE.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void setLabel() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/api/issues/%d/labels/%d", ISSUE.getId(), LABEL.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void setLabel_Issue_작성자_아닐때() {
        ResponseEntity<String> response = basicAuthTemplate(JUNGHYUN).getForEntity(String.format("/api/issues/%d/milestones/%d", ISSUE.getId(), MILESTONE.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}