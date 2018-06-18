package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest{

    private final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    public static final String DEFAULT_SUBJECT = "문제가 생겼습니다.";
    public static final String DEFAULT_COMMENT = "여기는 내용입니다.";

    @Autowired
    IssueRepository issueRepository;

    @After
    public void logout() {
        basicAuthTemplate.getForEntity("/users/logout", String.class);
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_fail_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create() throws Exception {

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", DEFAULT_SUBJECT)
                .addParameter("comment", DEFAULT_COMMENT).build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findBySubject(DEFAULT_SUBJECT));
    }

    @Test
    public void create_fail_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "asdfasdf")
                .addParameter("comment", DEFAULT_COMMENT).build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(issueRepository.findBySubject("asdfasdf"), is(Optional.empty()));
    }

    @Test
    public void show() {
        String location = createIssueLocation("서브젝트입니다.", "코멘트입니다.");
        ResponseEntity<String> response = template.getForEntity(location, String.class);
        assertTrue(response.getBody().contains("서브젝트입니다."));
        assertTrue(response.getBody().contains("코멘트입니다."));
    }

    @Test
    public void updateForm_success() {
        Issue issue = issueRepository.findById(1L).get();
        ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateForm_fail_other_user() {
        Issue issue = issueRepository.findById(1L).get();
        User other = findByUserId("sanjigi");
        ResponseEntity<String> response = basicAuthTemplate(other).getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void updateForm_fail_no_login() {
        Issue issue = issueRepository.findById(1L).get();
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

//    @Test
//    public void update_success() {
//
//    }
//
//    @Test
//    public void update_fail_other_user() {
//
//    }
//
//    @Test
//    public void update_fail_no_login() {
//
//    }
//
//    @Test
//    public void delete_success() {
//
//    }
//
//    @Test
//    public void delete_fail_other_user() {
//
//    }
//
//    @Test
//    public void delete_fail_no_login() {
//
//    }
}
