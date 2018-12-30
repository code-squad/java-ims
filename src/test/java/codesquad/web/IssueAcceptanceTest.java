package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {

    private static final Logger logger = getLogger(IssueAcceptanceTest.class);

    @Test
    public void 이슈작성_폼이동_성공_Test() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 이슈상세화면이동_Test() {
        ResponseEntity<String> responseEntity = template.getForEntity("/issues/1", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("이슈화면이동 Body : { }",  responseEntity.getBody());
    }
}
