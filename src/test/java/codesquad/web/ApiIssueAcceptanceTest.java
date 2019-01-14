package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueAcceptanceTest.class);

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

}
