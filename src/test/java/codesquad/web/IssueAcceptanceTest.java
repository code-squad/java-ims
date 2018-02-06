package codesquad.web;

import codesquad.domain.*;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);
    public static final User SANJIGI = new User(2L, "sanjigi", "test", "name");
    public static final Milestone MILESTONE = new Milestone(2L, "subject", new Date(), new Date());


    @Autowired
    IssueRepository issueRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다")
                .addParameter("comment", "내용입니다.").build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findById(1L));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
    }

    @Test
    public void showList() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("it's issue subject found by {}", issueRepository.findById(1L).map(Issue::getSubject).get());
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getSubject).get()), is(true));
    }

    @Test
    public void showOne() {
        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getComment).get()), is(true));
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getSubject).get()), is(true));
    }

    //update test를 위한 메소드
    private ResponseEntity<String> update(TestRestTemplate template, long updateTargetIssueId) throws Exception {
        String issueId = Long.toString(updateTargetIssueId);
        HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm().put();
        htmlFormDataBuilder.addParameter("subject", "제목입니다2")
                .addParameter("comment", "내용입니다2");
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();
        return template.postForEntity(String.format("/issues/%s", issueId), request, String.class);
    }

    @Test
    public void update_success() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate(), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), Is.is(HttpStatus.FOUND));
        assertThat(target.getSubject(), is("제목입니다2"));
        assertThat(target.getComment(), is("내용입니다2"));

    }

    @Test
    public void update_failTest_noLogin() throws Exception {
        ResponseEntity<String> response = update(template(), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), Is.is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_failTest_anotherUser() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate(SANJIGI), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), Is.is(HttpStatus.FORBIDDEN));
    }

    //delete test를 위한 메소드
    private ResponseEntity<String> delete(TestRestTemplate template, long updateTargetIssueId) throws Exception {
        String issueId = Long.toString(updateTargetIssueId);
        HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm().delete();
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();
        return template.postForEntity(String.format("/issues/%s", issueId), request, String.class);
    }

    @Test
    public void delete_success() throws Exception {
        ResponseEntity<String> response = delete(basicAuthTemplate(), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(target.isDeleted());

    }

    @Test
    public void delete_failTest_noLogin() throws Exception {
        ResponseEntity<String> response = delete(template(), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertTrue(!target.isDeleted());

    }

    @Test
    public void delete_failTest_anotherUser() throws Exception {
        ResponseEntity<String> response = delete(basicAuthTemplate(SANJIGI), 1L);
        Issue target = issueRepository.findOne(1L);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertTrue(!target.isDeleted());

    }

    @Test
    public void addMilestoneTest() throws Exception{
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/3/milestones/2", request, String.class);
        log.debug("it's !!: {}", issueRepository.findOne(3L).getMilestone());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }
}
