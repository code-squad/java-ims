package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest{

    public static final Long DEFAULT_MILESTONE_ID = 1L;
    public static final String DEFAULT_MILESTONE_URL = "/milestones/1";

    @Test
    public void showList() {
        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createForm_success() {
        // TODO Status OK with login user
    }

    @Test
    public void createForm_fail_no_login() {
        // TODO Status FORBIDDEN with anonymous user
    }

    @Test
    public void create_success() {
        // TODO Status FOUND with login user / check milestone schedule
    }

    @Test
    public void create_fail_no_login() {
        // TODO Status FORBIDDEN with anonymous user
    }




}
