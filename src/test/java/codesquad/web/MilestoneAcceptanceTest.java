package codesquad.web;

import codesquad.domain.Milestone;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create() {
        Milestone milestone = new Milestone("subject", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        ResponseEntity<String> response = template.postForEntity("/milestones", milestone, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void list() {
        ResponseEntity<String> response = template.getForEntity("/milestones/list", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
