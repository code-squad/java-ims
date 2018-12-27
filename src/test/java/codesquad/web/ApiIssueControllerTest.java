package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueControllerTest.class);

    @Test
    public void create() {
        Issue issue = new Issue("테스트 제목", "테스트 데이터입니다");
        String location = createResource("/api/issues", issue);

        log.info("location : " + location);
    }
}