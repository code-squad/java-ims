package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MilestoneControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MilestoneControllerTest.class);

    @Test
    public void form() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/milestones/form");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_fail_unAuthentication() {
        ResponseEntity<String> response = requestGet(template(), "/milestones/form");
        assertTrue(response.getBody().contains("Login Member"));
    }
}