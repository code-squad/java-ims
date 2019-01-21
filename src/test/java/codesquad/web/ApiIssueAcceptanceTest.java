package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.IssueFixture.ISSUE_BODY_JSON_PARSE_ERROR;
import static codesquad.domain.UserTest.RED;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = getLogger(ApiIssueAcceptanceTest.class);
    @Test
    public void addMilestone_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/api/issues/%d/milestones/%d", 1, 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void addMilestone_no_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/api/issues/%d/milestones/%d", 1, 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void create_issue() {
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        ResponseEntity<String> response = basicAuthTemplate(RED).postForEntity("/api/issues", issueBody, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void create_issue_no_login() {
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        ResponseEntity<String> response = template.postForEntity("/api/issues", issueBody, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void show() {
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String location = createResource(RED, "/api/issues", issueBody);
        logger.debug("===== : {}", location);
        
        ResponseEntity<Issue> response = template().getForEntity(location, Issue.class);
        logger.debug("===== : {}", response);
    }

    @Test
    public void create_answer() {
        // 이슈생성
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String location = createResource(RED, "/api/issues", issueBody);
        ResponseEntity<Issue> response = template().getForEntity(location, Issue.class);

        // 답변생성
        String answer = "answers";
        ResponseEntity<String> responseEntity = basicAuthTemplate(RED).postForEntity(String.format
                ("/api/issues/%d/answers", response.getBody().getId()), answer, String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void create_answer_no_login() {
        // 이슈 생성
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String location = createResource(RED, "/api/issues", issueBody);
        ResponseEntity<Issue> response = template().getForEntity(location, Issue.class);

        // 답변
        String answer = "answers";
        ResponseEntity<String> responseEntity = template().postForEntity(String.format
                ("/api/issues/%d/answers", response.getBody().getId()), answer, String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
