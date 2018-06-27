package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.UserTest;
import codesquad.dto.CommentDto;
import codesquad.dto.IssueDto;
import codesquad.dto.MilestoneDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    
    private static final Logger log = LoggerFactory.getLogger(ApiIssueAcceptanceTest.class);

    @Test
    public void setMilestone() {
        IssueDto issueDto = new IssueDto("test12", "testContents");
        log.debug("issueDtoTest : {}", issueDto);
        String issueLocation = createResourceOfIssue("/api/issues", issueDto, basicAuthTemplate());
        log.debug("issueLocation : {}", issueLocation);

        MilestoneDto milestoneDto = createMilestone("testMilestone1");
        String location = createResourceOfIssue("/api/milestones", milestoneDto, basicAuthTemplate());
        log.debug("location : {}", location);

        basicAuthTemplate().postForObject(issueLocation + location, makeRequest(), Milestone.class);
        Issue issue = getResource(issueLocation, Issue.class, findByUserId("javajigi"));

        assertThat(issue.getMilestone().getSubject(), is("testMilestone1"));
    }

    @Test
    public void setMilestone_no_owner() {
        IssueDto issueDto = new IssueDto("test13", "testContents");
        String issueLocation = createResourceOfIssue("/api/issues", issueDto, basicAuthTemplate());

        MilestoneDto milestoneDto = createMilestone("testMilestone2");
        String location = createResourceOfIssue("/api/milestones", milestoneDto, basicAuthTemplate());

        basicAuthTemplate(findByUserId("riverway")).postForObject(issueLocation + location, makeRequest(), Milestone.class);
        Issue issue = getResource(issueLocation, Issue.class, findByUserId("javajigi"));

        assertNull(issue.getMilestone());
    }

    @Test
    public void setLabel(){
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issues/1/labels/1", makeRequest(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Issue issue = getResource("/api/issues/1", Issue.class, findByUserId("javajigi"));
        assertThat(issue.getLabel().getId(), is(1L));
    }

    @Test
    public void setLabel_no_owner(){
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("riverway")).postForEntity("/api/issues/3/labels/1", makeRequest(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void setAssignee(){
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issues/1/assignees/3", makeRequest(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Issue issue = getResource("/api/issues/1", Issue.class, findByUserId("javajigi"));
        assertThat(issue.getAssignee().getId(), is(3L));
    }

    @Test
    public void setAssignee_no_owner(){
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issues/2/assignees/3", makeRequest(), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private HttpEntity<MultiValueMap<String, Object>> makeRequest() {
        return HtmlFormDataBuilder.urlEncodedForm().addParameter("_method", "put").build();
    }

    private MilestoneDto createMilestone(String subject) {
        return new MilestoneDto(subject, LocalDateTime.parse("2017-06-01T08:30"), LocalDateTime.parse("2017-06-25T15:30"));
    }

    @Test
    public void addComment() {
        CommentDto comment = new CommentDto("comments");
        String location = createResourceOfIssue("/api/issues/1/comments", comment, basicAuthTemplate());

        Comment dbComment = getResource(location, Comment.class, findDefaultUser());
        assertThat(dbComment.getComment(), is(comment.getComment()));
        assertThat(dbComment.getIssue().getId(), is(1L));
    }

    @Test
    public void addComment_no_login() {
        CommentDto comment = new CommentDto("comments");
        ResponseEntity<String> response = template().postForEntity("/api/issues/1/comments", comment, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void updateComment() {
        CommentDto comment = new CommentDto("comments");
        String location = createResourceOfIssue("/api/issues/1/comments", comment, basicAuthTemplate());

        CommentDto update = new CommentDto("update");
        basicAuthTemplate().put(location, update);

        Comment dbComment = getResource(location, Comment.class, findDefaultUser());

        assertThat(dbComment.getComment(), is(update.getComment()));
    }

    @Test
    public void updateComment_no_owner() {
        CommentDto comment = new CommentDto("comments");
        String location = createResourceOfIssue("/api/issues/1/comments", comment, basicAuthTemplate());

        CommentDto update = new CommentDto("update");
        basicAuthTemplate(findByUserId("riverway")).put(location, update);

        Comment dbComment = getResource(location, Comment.class, findDefaultUser());

        assertThat(dbComment.getComment(), is(comment.getComment()));
    }
}
