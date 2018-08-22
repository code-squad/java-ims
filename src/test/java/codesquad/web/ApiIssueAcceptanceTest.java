package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    @Test
    public void setMilestone() {
        Long issueId = 1L;
        Long milestoneId = 1L;
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/milestones/" + milestoneId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
