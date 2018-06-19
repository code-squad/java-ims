package codesquad.web;

import codesquad.dto.MilestoneDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

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
    public void create_success() {
        MilestoneDto newMilestoneDto = new MilestoneDto(LocalDateTime.now(), LocalDateTime.now());
        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", newMilestoneDto, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        // TODO Status FOUND with login user / check milestone schedule
    }

    @Test
    public void create_fail_no_login() {
        MilestoneDto newMilestoneDto = new MilestoneDto(LocalDateTime.now(), LocalDateTime.now());
        ResponseEntity<String> response = template.postForEntity("/milestones", newMilestoneDto, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        // TODO Status FOUND with login user / check milestone schedule
    }






}
