package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.web.IssueAcceptanceTest.requestCreateIssue;
import static codesquad.web.MilestoneAcceptanceTest.requestCreateMilestone;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiIssueControllerTest extends AcceptanceTest {

    @Test
    public void setMilestone() {
        String path = getIssuePath() + getMilestonePath(true);
        ResponseEntity<Issue> response = requestGet(basicAuthTemplate(), path, Issue.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setMilestone_fail_unAuthentication() {
        String path = getIssuePath() + getMilestonePath(true);
        ResponseEntity<Void> response = requestGet(template(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void setMilestone_fail_invalidMilestoneId() {
        String path = getIssuePath() + getMilestonePath(false);
        ResponseEntity<Void> response = requestGet(basicAuthTemplate(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void setLabel() {
        String path = getIssuePath() + getLabelPath(true);
        ResponseEntity<Issue> response = requestGet(basicAuthTemplate(), path, Issue.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setLabel_fail_unAuthentication() {
        String path = getIssuePath() + getLabelPath(true);
        ResponseEntity<Void> response = requestGet(template(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void setLabel_fail_invalidLabelId() {
        String path = getIssuePath() + getLabelPath(false);
        ResponseEntity<Void> response = requestGet(basicAuthTemplate(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void setAssignee() {
        String path = getIssuePath() + getAssigneePath(true);
        ResponseEntity<Issue> response = requestGet(basicAuthTemplate(), path, Issue.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setAssignee_fail_unAuthentication() {
        String path = getIssuePath() + getAssigneePath(true);
        ResponseEntity<Void> response = requestGet(template(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void setAssignee_fail_invalidAssigneeId() {
        String path = getIssuePath() + getAssigneePath(false);
        ResponseEntity<Void> response = requestGet(basicAuthTemplate(), path, Void.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    private String getIssuePath() {
        return getIssuePath("test subject", "test comment");
    }

    private String getIssuePath(String subject, String comment) {
        return "/api" + getPath(requestPost(basicAuthTemplate(), "/issues", requestCreateIssue(subject, comment)));
    }

    private String getMilestonePath(boolean isExist) {
        if (isExist) {
            String path = getPath(requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone("subject")));
            return "/setMilestone/" + path.charAt(path.length() - 1);
        }
        return "/setMilestone/1000";
    }

    private String getAssigneePath(boolean isExist) {
        if (isExist) {
            return "/setAssignee/3";
        }
        return "/setAssignee/100";
    }

    private String getLabelPath(boolean isExist) {
        if (isExist) {
            return "/setLabel/1";
        }
        return "/setLabel/100";
    }
}