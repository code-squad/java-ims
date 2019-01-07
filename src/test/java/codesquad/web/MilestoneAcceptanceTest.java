package codesquad.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LogManager.getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void listShow() {
        ResponseEntity<String> response = template.getForEntity("/milestone/list", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void formShow() {
        ResponseEntity<String> response = template.getForEntity("/milestone/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

}
