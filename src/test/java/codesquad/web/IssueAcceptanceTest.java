package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;
import support.test.IssueFixture;
import support.test.UserFixture;

import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {

    private static final Logger logger = getLogger(IssueAcceptanceTest.class);

    @Test
    public void 이슈작성_폼이동_성공_Test() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 이슈작성_폼이동_로그인X_실패_Test() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 이슈상세화면이동_Test() {
        ResponseEntity<String> responseEntity = template.getForEntity("/issues/1", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("이슈화면이동 Body : { }",  responseEntity.getBody());
    }
}
