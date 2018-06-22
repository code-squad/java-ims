package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.UserTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public class IssueAcceptanceTest extends AcceptanceTest {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", "testIssue")
                .addParameter("contents", "testContents").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(issueRepository.count(), is(beforeCount+1));
    }

    @Test
    public void create_제목_내용_없음() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", "")
                .addParameter("contents", "").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(issueRepository.count(), is(beforeCount));
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", "testIssue2")
                .addParameter("contents", "testContents2").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = template().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(issueRepository.count(), is(beforeCount));
    }

    @Test
    public void createForm(){
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void show(){
        ResponseEntity<String> response = template().getForEntity("/issues/3", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("test3"), is(true));
    }

    @Test
    public void updateForm(){
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/1/form", String.class);
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
        Issue dbIssue = issueRepository.findById(1L).get();
        assertTrue(response.getBody().contains(dbIssue.getTitle()));
    }

    @Test
    public void updateForm_다른_사용자(){
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("riverway")).getForEntity("/issues/1/form", String.class);
        assertThat(response.getStatusCode(),is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update() throws Exception{
        ResponseEntity<String> response = update(basicAuthTemplate(), 1);
        assertThat(response.getStatusCode(),is(HttpStatus.FOUND));
        Issue dbIssue = issueRepository.findById(1L).get();
        assertThat(dbIssue.getTitle(), is("updateIssue"));
    }

    @Test
    public void update_다른_사용자() throws Exception{
        ResponseEntity<String> response = update(basicAuthTemplate(findByUserId("riverway")), 4);
        assertThat(response.getStatusCode(),is(HttpStatus.FORBIDDEN));
        Issue dbIssue = issueRepository.findById(4L).get();
        assertThat(dbIssue.getTitle(), is("test4"));
    }

    private ResponseEntity<String> update(TestRestTemplate template, long number) throws Exception{
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", "updateIssue")
                .addParameter("contents", "updateContents")
                .addParameter("_method", "put").build();
        return template.postForEntity(String.format("/issues/%d", number), request, String.class);
    }

    @Test
    public void delete(){
       ResponseEntity<String> response = delete(basicAuthTemplate(findByUserId("riverway")),2L);

       Optional<Issue> dbissue = issueRepository.findById(2L);
       assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
       assertThat(dbissue.isPresent(), is(false));
    }

    @Test
    public void delete_다른_사용자(){
        ResponseEntity<String> response = delete(basicAuthTemplate(findByUserId("sanjigi")), 3L);

        Optional<Issue> dbissue = issueRepository.findById(3L);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(dbissue.isPresent(), is(true));
    }

    private ResponseEntity<String> delete(TestRestTemplate template, long number){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
        return template.postForEntity(String.format("/issues/%d", number), request, String.class);
    }

    @Test
    public void setMilestone(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/1/milestones/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        Issue issue = issueRepository.findById(1L).get();
        assertThat(issue.getMilestone().getId(), is(1L));
    }

    @Test
    public void setMilestone_no_owner_of_issue(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/2/milestones/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void setMilestone_no_login(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = template().postForEntity("/issues/2/milestones/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        Issue issue = issueRepository.findById(1L).get();
        assertNull(issue.getMilestone());
    }

    @Test
    public void setMilestone_다른_마일스톤_지정(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/1/milestones/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        Issue issue = issueRepository.findById(1L).get();
        assertThat(issue.getMilestone().getId(), is(1L));

        response = basicAuthTemplate().postForEntity("/issues/1/milestones/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        issue = issueRepository.findById(1L).get();
        assertThat(issue.getMilestone().getId(), is(1L));
    }

    @Test
    public void setAssignee(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/3/assignees/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        Issue issue = issueRepository.findById(3L).get();
        assertThat(issue.getAssignee().getId(), is(2L));
    }

    @Test
    public void setAssignee_no_owner_of_issue(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("riverway")).postForEntity("/issues/4/assignees/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void setAssignee_다른_담당자_지정(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/3/assignees/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        Issue issue = issueRepository.findById(3L).get();
        assertThat(issue.getAssignee().getId(), is(2L));

        response = basicAuthTemplate().postForEntity("/issues/3/assignees/3", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        issue = issueRepository.findById(3L).get();
        assertThat(issue.getAssignee().getId(), is(3L));
    }

    @Test
    public void setLabel(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/1/labels/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        Issue issue = issueRepository.findById(1L).get();
        assertThat(issue.getLabel().getId(), is(1L));
    }

    @Test
    public void setLabel_no_owner_of_issue(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();

        ResponseEntity<String> response = template().postForEntity("/issues/2/labels/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void close(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/4/close", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        Issue issue = issueRepository.findById(4L).get();
        assertThat(issue.isClosed(), is(true));
    }

    @Test
    public void close_다른_사용자(){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put").build();

        ResponseEntity<String> response = basicAuthTemplate(UserTest.SANJIGI).postForEntity("/issues/4/close", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}
