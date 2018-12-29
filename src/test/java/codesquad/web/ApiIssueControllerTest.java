package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.IssueTest.ISSUE;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueControllerTest.class);

    @Test
    public void show() {
        String location = createResource("/api/issues", ISSUE);
        ResponseEntity<Issue> responseEntity = template().getForEntity(location, Issue.class);
        softly.assertThat(responseEntity.getBody().hasSameSubjectAndComment(ISSUE)).isEqualTo(true);
    }
}