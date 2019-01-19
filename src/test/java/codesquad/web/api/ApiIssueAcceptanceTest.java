package codesquad.web.api;

import codesquad.domain.issue.Issue;
import codesquad.domain.label.Label;
import codesquad.domain.milestone.Milestone;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.UserTest.SANJIGI;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiIssueAcceptanceTest.class);

    private String location;

    @Before
    public void setUp() throws Exception {
        location = createResource("/api/issues", ORIGINAL_ISSUE);
    }

    @Test
    public void create_no_login() {
        ResponseEntity<Void> response = template.postForEntity("/api/issues", ORIGINAL_ISSUE, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void create_login() {
        ResponseEntity<Void> response = basicAuthTemplate().postForEntity("/api/issues", ORIGINAL_ISSUE, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

//        String location = createResource("/api/issues", JAVAJIGI);
//        ResponseEntity<String> response = getResource(location, findDefaultUser());
//        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void update_no_login() {
        ResponseEntity<Issue> responseEntity =
                template.exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_ISSUE), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_login() {
        ResponseEntity<Issue> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_ISSUE), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void update_other_user() {
        ResponseEntity<Issue> responseEntity =
                basicAuthTemplate(SANJIGI).exchange(location, HttpMethod.PUT, createHttpEntity(UPDATED_ISSUE_2), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_no_login() {
        ResponseEntity<Issue> responseEntity =
                template.exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_login() {
        ResponseEntity<Issue> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void delete_other_user() {
        ResponseEntity<Issue> responseEntity =
                basicAuthTemplate(SANJIGI).exchange(location, HttpMethod.DELETE, HttpEntity.EMPTY, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void setMilestone() {
        ResponseEntity<Milestone> responseEntity =
                basicAuthTemplate().getForEntity("/api/issues/1/setMilestone/1", Milestone.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void setAssignee() {
        ResponseEntity<User> responseEntity =
                basicAuthTemplate().getForEntity("/api/issues/1/setAssignee/1", User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void setLabel() {
        ResponseEntity<Label> responseEntity =
                basicAuthTemplate().getForEntity("/api/issues/1/setLabel/1", Label.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
