package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.assertj.core.condition.Not;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);
    @Autowired
    private IssueService issueService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createForm_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_login() {
        String subject = "제모오오오옥";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "코메에에에에에에엔트")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/issues/"));
    }

    @Test
    public void create_no_login() {
        String subject = "들어가면 안되";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "제바아아아아아아알")
                .build();

        ResponseEntity<String> response = template().postForEntity("/issues", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);

        logger.debug("body : {}", response.getBody());
        assertTrue(response.getBody().contains("test issue1"));
        assertTrue(response.getBody().contains("테스트 1번 이슈입니다."));
    }

    @Test
    public void update_form_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/1/form", String.class);

        logger.debug("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("test issue1"));
        assertTrue(response.getBody().contains("테스트 1번 이슈입니다."));
    }

    @Test
    public void update_form_other() {
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("boobby")).getForEntity("/issues/1/form", String.class);

        logger.debug("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("작성자만 수정할 수 있습니다."));
    }

    @Test
    public void update_login() {
        String subject = "수정이 되야합니다.";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", subject)
                .addParameter("comment", "코메에에에에에에엔트")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/3", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(issueService.findById(3L).getSubject(), is(subject));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues/3"));
    }

    @Test
    public void update_no_login() {
        String subject = "수정이 되면 안됩니다!!!!!!";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", subject)
                .addParameter("comment", "코메에에에에에에엔트")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertNotEquals(issueService.findById(2L).getSubject(), subject);
    }

    @Test
    public void delete_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/5", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNull(issueService.findById(5L));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void delete_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/4", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertNotNull(issueService.findById(4L));
    }

    @Test
    public void set_milestone_writer() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .build();

        ResponseEntity<String> response = basicAuthTemplate()
                .postForEntity("/issues/1/setMilestone/1", request, String.class);

        Issue issue = issueService.findById(1L);
        Milestone milestone = milestoneService.findOne(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(issue.getMilestone(), is(milestone));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues/1"));
    }

    @Test
    public void set_milestone_other() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .build();

        ResponseEntity<String> response = basicAuthTemplate(findByUserId("boobby"))
                .postForEntity("/issues/1/setMilestone/2", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void set_assignee_writer() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .build();

        ResponseEntity<String> response = basicAuthTemplate()
                .postForEntity("/issues/1/setAssignee/3", request, String.class);

        Issue issue = issueService.findById(1L);
        User assignee = userRepository.findOne(3L);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(issue.getAssignee(), is(assignee));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues/1"));
    }

    @Test
    public void set_assignee_other() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .build();

        ResponseEntity<String> response = basicAuthTemplate(findByUserId("boobby"))
                .postForEntity("/issues/1/setAssignee/2", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}