package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.web.MilestoneAcceptanceTest.requestCreateMilestone;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    private static final String CREATE_PATH = "/issues";

    private static HttpEntity<MultiValueMap<String, Object>> requestCreateIssue(String subject, String comment) {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("subject", subject);
        builder.addParameter("comment", comment);
        return builder.build();
    }

    @Test
    public void form() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/form");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_fail_not_login() {
        ResponseEntity<String> response = requestGet(template(), "/issues/form");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void create() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment"));
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("redirect uri : {}", response.getHeaders().getLocation().getPath());
    }

    @Test
    public void create_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), CREATE_PATH, requestCreateIssue("test subject", "test comment"));
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void create_fail_invalid_params() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("t", "t"));
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment"));
        String path = getPath(response);

        requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone());

        response = requestGet(path);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("test subject"));
    }

    @Test
    public void show_fail_not_found() {
        ResponseEntity<String> response = requestGet("/issues/1000");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void edit() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment"))) + "/edit";
        log.debug("edit path : {}", editPath);
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), editPath);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void edit_fail_not_found() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/100/edit");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void edit_fail_unAuthentication() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment"))) + "/edit";
        ResponseEntity<String> response = requestGet(template(), editPath);
        assertTrue(response.getBody().contains("Login Member"));
    }

    @Test
    public void edit_fail_unAuthorized() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment"))) + "/edit";
        ResponseEntity<String> response = requestGet(basicAuthTemplate(findByUserId("sanjigi")), editPath);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private ResponseEntity<String> update(TestRestTemplate template) {
        String path = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));

        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("_method", "put");
        builder.addParameter("subject", "modify subject");
        builder.addParameter("comment", "modify content");
        return template.postForEntity(path, builder.build(), String.class);
    }

    @Test
    public void update() {
        ResponseEntity<String> response = update(basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void update_fail_unAuthentication() {
        ResponseEntity<String> response = update(template());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void update_fail_unAuthorized() {
        ResponseEntity<String> response = update(basicAuthTemplate(findByUserId("sanjigi")));
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private ResponseEntity<String> delete(TestRestTemplate template) {
        String path = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("_method", "delete");
        return template.postForEntity(path, builder.build(), String.class);
    }

    @Test
    public void delete() {
        ResponseEntity<String> response = delete(basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void delete_fail_unAuthentication() {
        ResponseEntity<String> response = delete(template());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void delete_fail_unAuthorized() {
        ResponseEntity<String> response = delete(basicAuthTemplate(findByUserId("sanjigi")));
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void selectMilestone() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone());

        ResponseEntity<String> response = requestGet(basicAuthTemplate(), issuePath + "/setMilestone/1");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void selectMilestone_fail_unAuthentication() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone());

        ResponseEntity<String> response = requestGet(template(), issuePath + "/setMilestone/1");
        assertTrue(response.getBody().contains("Login Member"));
    }

    @Test
    public void selectMilestone_fail_invalid_Issue_Id() {
        String issuePath = "/issues/100";
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), issuePath + "/setMilestone/1");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void selectMilestone_fail_invalid_Milestone_Id() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone());

        ResponseEntity<String> response = requestGet(basicAuthTemplate(), issuePath + "/setMilestone/100");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void selectAssignee() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), issuePath + "/setAssignee/1");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void selectAssignee_unAuthentication() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        ResponseEntity<String> response = requestGet(template(), issuePath + "/setAssignee/1");
        assertTrue(response.getBody().contains("Login Member"));
    }

    @Test
    public void selectAssignee_invalid_Issue_Id() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/100/setAssignee/1");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void selectAssignee_invalid_User_Id() {
        String issuePath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, requestCreateIssue("test subject", "test comment")));
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), issuePath + "/setAssignee/100");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
