package codesquad.web;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.*;
import support.test.*;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueAcceptanceTest extends BasicAuthAcceptanceTest {
    
    private static final Logger logger = getLogger(ApiIssueAcceptanceTest.class);
    
    @Test
    public void 이슈작성_글자수5자리미만_실패_Test() {
        ResponseEntity<Issue> responseEntity = basicAuthTemplate()
                                                .postForEntity("/api/issues", IssueFixture.FAIL_ISSUE_CONTENT, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 이슈작성_내용미입력_실패_Test() {
        ResponseEntity<Issue> responseEntity = basicAuthTemplate().postForEntity("/api/issues", IssueFixture.FAIL_ISSUE_NOT_CONTENT, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 이슈작성_성공_Test() {
        ResponseEntity<Issue> responseEntity = basicAuthTemplate().postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void 이슈작성_로그인X_Test() {
        ResponseEntity<Issue> responseEntity = template.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 이슈삭제_로그인X_실패_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈삭제 */
        ResponseEntity<Void> response = template()
                .exchange(location, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 이슈삭제_로그인O_자신글X_실패_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈삭제 */
        ResponseEntity<Void> response = basicAuthTemplate(UserFixture.DOBY)
                .exchange(location, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 이슈삭제_로그인O_자신글O_성공_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈삭제 */
        ResponseEntity<Void> response = basicAuthTemplate.exchange(location, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()), Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 이슈수정_로그인O_본인O_글자수정상_성공_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈수정 */
        IssueDto issue = IssueFixture.SUCCESS_ISSUE;
        issue.setSubject("Modified Subject");
        responseEntity = basicAuthTemplate.exchange(location, HttpMethod.PUT, new HttpEntity<>(issue, new HttpHeaders()), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void 이슈수정_로그인X_본인O_글자수정상_실패_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈수정 */
        IssueDto issue = IssueFixture.SUCCESS_ISSUE;
        issue.setSubject("Modified Subject");
        responseEntity = template.exchange(location, HttpMethod.PUT, new HttpEntity<>(issue, new HttpHeaders()), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 이슈수정_로그인O_본인X_글자수정상_실패_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈수정 */
        IssueDto issue = IssueFixture.SUCCESS_ISSUE;
        issue.setSubject("Modified Subject");
        responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .exchange(location, HttpMethod.PUT, new HttpEntity<>(issue, new HttpHeaders()), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 이슈수정_로그인O_본인O_글자수비정상_실패_Test() {
        /* 1. 이슈작성 */
        ResponseEntity<Issue> responseEntity = basicAuthTemplate.postForEntity("/api/issues", IssueFixture.SUCCESS_ISSUE, Issue.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 이슈수정 */
        IssueDto issue = IssueFixture.FAIL_ISSUE_CONTENT;
        issue.setSubject("Modified Subject");
        responseEntity = basicAuthTemplate()
                .exchange(location, HttpMethod.PUT, new HttpEntity<>(issue, new HttpHeaders()), Issue.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
