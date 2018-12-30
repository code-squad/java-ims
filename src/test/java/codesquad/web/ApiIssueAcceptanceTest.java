package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.*;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    @Test
    public void 이슈작성_글자수5자리미만_실패_Test() {
        ResponseEntity<Issue> responseEntity = template.postForEntity("/api/issues", IssueFixture.FAIL_ISSUE_CONTENT, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 이슈작성_내용미입력_실패_Test() {
        ResponseEntity<Issue> responseEntity = template.postForEntity("/api/issues", IssueFixture.FAIL_ISSUE_NOT_CONTENT, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 이슈작성_성공_Test() {
        ResponseEntity<Issue> responseEntity = template.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
