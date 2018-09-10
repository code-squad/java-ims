package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiIssueAcceptanceTest.class);

    @Test
    public void setMilestone() {
        Long issueId = 1L;
        Long milestoneId = 1L;
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/milestones/" + milestoneId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setLabel() {
        Long issueId = 1L;
        Long labelId = 1L;
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/labels/" + labelId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setAsignee() {
        Long issueId = 1L;
        Long assigneeId = 1L; // equal to userId
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/users/" + assigneeId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
