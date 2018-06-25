package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.MilestoneDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(ApiIssueAcceptanceTest.class);
    private static final String SET_MILESTONE_PATH = "/api/issues/1/setMilestone/1";
    private static final String DEFAULT_MILESTONE_SUBJECT = "Test Subject";
    private static final String SET_ASSIGNEE_PATH = "/api/issues/1/setAssignee/1";
    private static final String DEFAULT_ASSIGNEE = "javajigi";
    private static final String SET_LABLE_PATH = "/api/issues/1/setLabel/1";

    private String setAttribute(TestRestTemplate template, String path) {
        ResponseEntity<String> response = template.getForEntity(path, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        return response.getHeaders().getLocation().getPath();
    }

    private <T> T getResource(String location, Class<T> responseType, TestRestTemplate template) {
        return template.getForObject(location, responseType);
    }

    @Test
    public void setMilestone() {
        Issue response = getResource(SET_MILESTONE_PATH, Issue.class, basicAuthTemplate());
        assertThat(response.getMilestone().getSubject(), is(DEFAULT_MILESTONE_SUBJECT));
    }

    @Test
    public void setMilestone_NOT_Logged_In() {
        Issue response = getResource(SET_MILESTONE_PATH, Issue.class, template());
        assertNull(response);
    }

    @Test
    public void setAssignee() {
        Issue response = getResource(SET_ASSIGNEE_PATH, Issue.class, basicAuthTemplate());
        assertThat(response.getAssignee().getUserId(), is(DEFAULT_ASSIGNEE));
    }

    @Test
    public void setAssignee_NOT_Logged_In() {
        Issue response = getResource(SET_ASSIGNEE_PATH, Issue.class, template());
        assertNull(response);
    }

    @Test
    public void setLabel() {
        Issue response = getResource(SET_LABLE_PATH, Issue.class, basicAuthTemplate());
        assertThat(response.getLabel(), is(Label.JAVA));
    }

    @Test
    public void setLabel_NOT_Logged_In() {
        Issue response = getResource(SET_LABLE_PATH, Issue.class, template());
        assertNull(response);
    }

    @Test
    public void addComment() {

    }
}