package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BaseTest;
import support.test.BasicAuthAcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class MainAcceptanceTest extends BasicAuthAcceptanceTest {

    private static final Logger logger = getLogger(MainAcceptanceTest.class);

    @Test
    public void 홈화면이동_Test() {
        ResponseEntity<String> responseEntity = template.getForEntity("/", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("홈화면이동 Body : { }",  responseEntity.getBody());
    }
}
