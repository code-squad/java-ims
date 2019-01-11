package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.ISSUES;

public class HomeControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(HomeControllerTest.class);

    @Test
    public void home() {
        ResponseEntity<String> responseEntity = template.getForEntity("/", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (Issue issue : ISSUES) {
            IssueBody issueBody = issue.getIssueBody();
            softly.assertThat(responseEntity.getBody().contains(issueBody.getSubject())).isTrue();
            softly.assertThat(responseEntity.getBody().contains(issue.getWriter().getName())).isTrue();
        }
        log.debug("response : {}", responseEntity.getBody());
    }

    @Test
    public void 로그인_안했을때_네비게이션() {
        ResponseEntity<String> responseEntity = template.getForEntity("/", String.class);
        softly.assertThat(responseEntity.getBody().contains("login")).isTrue();
        softly.assertThat(responseEntity.getBody().contains("join")).isTrue();
        log.debug("response : {}", responseEntity.getBody());
    }

    @Test
    public void 로그인_했을때_네비게이션() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity("/", String.class);
        softly.assertThat(responseEntity.getBody().contains("logout")).isTrue();
        softly.assertThat(responseEntity.getBody().contains("my")).isTrue();;
        log.debug("response : {}", responseEntity.getBody());
    }
}