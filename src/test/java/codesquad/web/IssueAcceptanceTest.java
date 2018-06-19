package codesquad.web;

import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.MilestoneDto;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest{

    private final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    public static final String DEFAULT_SUBJECT = "이슈입니다";
    public static final String DEFAULT_COMMENT = "코멘트입니다";

    public static final Long DEFAULT_ISSUE_ID = 1L;
    public static final String DEFAULT_ISSUE_URL = "/issues/1";

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
        final String newSubject = "서브젝트이에요";
        final String newComment = "코멘트으이에요";

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", newSubject)
                .addParameter("comment", newComment).build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findBySubject(newSubject));
    }

    @Test
    public void create_fail_no_login() throws Exception {
        final String newSubject = "서브젝트이에요2";
        final String newComment = "코멘트으이에요2";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", newSubject)
                .addParameter("comment", newComment).build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(issueRepository.findBySubject(newSubject), is(Optional.empty()));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = template.getForEntity(DEFAULT_ISSUE_URL, String.class);
        log.debug("body : {}", response.getBody());
        assertTrue(response.getBody().contains(DEFAULT_SUBJECT));
        assertTrue(response.getBody().contains(DEFAULT_COMMENT));
    }

    @Test
    public void updateForm_success() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", DEFAULT_ISSUE_ID), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateForm_fail_other_user() {
        User other = findByUserId("sanjigi");
        ResponseEntity<String> response = basicAuthTemplate(other).getForEntity(String.format("/issues/%d/form", DEFAULT_ISSUE_ID), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void updateForm_fail_no_login() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", DEFAULT_ISSUE_ID), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_success() {
        IssueDto updateIssue = new IssueDto()
                .setComment("코멘트를 바꾸려고 합니다.")
                .setSubject("제목을 바꾸려고 합니다.");
        basicAuthTemplate.put(DEFAULT_ISSUE_URL, updateIssue);
        ResponseEntity<String> response = getResource(DEFAULT_ISSUE_URL, loginUser);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(DEFAULT_COMMENT), is(true));
    }

    @Test
    public void update_fail_other_user() {
        IssueDto updateIssue = new IssueDto()
                .setComment("코멘트를 바꾸려고 합니다.")
                .setSubject("제목을 바꾸려고 합니다.");
        User other = findByUserId("sanjigi");
        basicAuthTemplate(other).put(DEFAULT_ISSUE_URL, updateIssue);
        ResponseEntity<String> response = getResource(DEFAULT_ISSUE_URL, loginUser);
        assertThat(response.getBody().contains(DEFAULT_COMMENT), is(true));
    }

    @Test
    public void update_fail_no_login() {
        IssueDto updateIssue = new IssueDto()
                .setComment("코멘트를 바꾸려고 합니다.")
                .setSubject("제목을 바꾸려고 합니다.");
        template.put(DEFAULT_ISSUE_URL, updateIssue);
        ResponseEntity<String> response = getResource(DEFAULT_ISSUE_URL, loginUser);
        assertThat(response.getBody().contains(DEFAULT_COMMENT), is(true));
    }


    @Test
    public void delete_success() {
        String location = createIssueLocation("delete test1", "test comment");
        log.debug("created location : {}", location);
        basicAuthTemplate.delete(location);
        ResponseEntity<String> response = getResource(location, loginUser);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void delete_fail_other_user() {
        String location = createIssueLocation("delete test2", "test comment");
        User other = findByUserId("sanjigi");
        basicAuthTemplate(other).delete(location);
        ResponseEntity<String> response = getResource(location, loginUser);
        assertThat(response.getBody().contains("delete test2"), is(true));
    }

    @Test
    public void delete_fail_no_login() {
        String location = createIssueLocation("delete test3", "test comment");
        template.delete(location);
        ResponseEntity<String> response = getResource(location, loginUser);
        assertThat(response.getBody().contains("delete test3"), is(true));
    }

//    @Test
//    public void add_milestone() {
//        String location = createIssueLocation("delete test4", "test comment");
//        MilestoneDto newMilestoneDto = new MilestoneDto("Hellloo2", LocalDateTime.now(), LocalDateTime.now());
//        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", newMilestoneDto, String.class);
//        log.debug("milestone path {}", response.getHeaders().getLocation().getPath());
//        ResponseEntity<String> response1 = basicAuthTemplate.getForEntity(location, String.class);
//    }
}
