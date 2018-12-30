package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class HomeAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(HomeAcceptanceTest.class);

    @Test
    public void list() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }
}
