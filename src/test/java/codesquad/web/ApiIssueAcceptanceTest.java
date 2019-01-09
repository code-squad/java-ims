package codesquad.web;

import codesquad.domain.UserTest;
import codesquad.domain.Issue;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueAcceptanceTest.class);

    private Issue issue;

    @Before
    public void setUp() {
        Issue issueOne = new Issue(1L,"subject","comment", UserTest.USER, false);
        issue = issueOne;
    }

    @Test
    public void create() {

        ResponseEntity<Issue> response = basicAuthTemplate().postForEntity("/api/issues", issue, Issue.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(response.getBody().getSubject()).isEqualTo("subject");
        softly.assertThat(response.getBody().getComment()).isEqualTo("comment");

        String location = response.getHeaders().getLocation().getPath();
        log.debug(" location : ~~~~~ {} ", location);
        ResponseEntity<String> responseEntity = template.getForEntity(location, String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create_without_login() {
        ResponseEntity<Issue> response = template().postForEntity("/api/issues", issue, Issue.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void locationUpdate() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issue.getId(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update() {
        String location = createResource("/api/issues", issue);

        Issue updateIssue = new Issue("update subject", "update comment", UserTest.USER, false);
        ResponseEntity<Issue> responseEntity = basicAuthTemplate()
                .exchange(location, HttpMethod.PUT, createHttpEntity(updateIssue),Issue.class);
        softly.assertThat(updateIssue.equalsQuestion(responseEntity.getBody())).isTrue();
    }

    @Test
    public void update_no_login() {
        String location = createResource("/api/issues", issue);

        Issue updateIssue = new Issue("update subject", "update comment", null, false);
        ResponseEntity<Issue> responseEntity = basicAuthTemplate()
                .exchange("/api/issues/1", HttpMethod.PUT, createHttpEntity(updateIssue),Issue.class);
    }

}
