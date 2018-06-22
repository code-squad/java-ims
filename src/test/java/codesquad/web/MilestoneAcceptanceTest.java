package codesquad.web;

import codesquad.dto.MilestoneDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import java.text.ParseException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest{

    private final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    public static final Long DEFAULT_MILESTONE_ID = 1L;
    public static final String DEFAULT_MILESTONE_URL = "/milestones/1";

    @Test
    public void showList() {
        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createForm_success() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/milestones/form", String.class);
        log.debug("response headers : {}", response.getHeaders());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createForm_fail_no_login() {
        ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
        log.debug("response : {}", response);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_success() throws ParseException {
        MilestoneDto newMilestoneDto = new MilestoneDto("Hellloo1", "2018-02-01T10:15", "2018-05-01T10:15");
        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", newMilestoneDto, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void create_fail_no_login() throws ParseException {
        MilestoneDto newMilestoneDto = new MilestoneDto("Hellloo2", "2018-02-02T10:15", "2018-06-01T10:15");
        ResponseEntity<String> response = template.postForEntity("/milestones", newMilestoneDto, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

}
