package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.MilestoneTest.MILESTONE1;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("subject", "testSubject")
                        .addParameter("comment", "testComment")
                        .addParameter("writer", JAVAJIGI.getId())
                        .build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("subject", "testSubject")
                        .addParameter("comment", "testComment")
                        .addParameter("writer", JAVAJIGI.getId())
                        .build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findBySubject("testSubject")).isNotNull();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void show_not_exist_issue() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 99999999), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void show() {
        for (Issue issue : ISSUES) {
            ResponseEntity<String> response = template().getForEntity(String.format("/issues/%d", issue.getId()), String.class);

            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            log.debug("kkk body : {}", response.getBody());
            softly.assertThat(response.getBody()).contains(issueRepository.findById(issue.getId()).get().getSubject());
            softly.assertThat(response.getBody()).contains(issueRepository.findById(issue.getId()).get().getComment());
        }
    }

    @Test
    public void updateForm_no_login() {
        ResponseEntity<String> response = template()
                .getForEntity(String.format("/issues/%d/form", ISSUE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void updateForm_not_owner() {
        ResponseEntity<String> response = basicAuthTemplate(SANJIGI)
                .getForEntity(String.format("/issues/%d/form", ISSUE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void updateForm() {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/issues/%d/form", ISSUE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(ISSUE1.getSubject())).isTrue();
        softly.assertThat(response.getBody().contains(ISSUE1.getComment())).isTrue();
    }

    @Test
    public void update_not_owner() {
        ResponseEntity<String> response = update(basicAuthTemplate(SANJIGI));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<String> update(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", "updatedTestSubject")
                .addParameter("comment", "updatedTestComment")
                .addParameter("writer", JAVAJIGI.getId())
                .build();

        return template.postForEntity(String.format("/issues/%d", ISSUE2.getId()), request, String.class);
    }

    @Test
    public void update() {
        ResponseEntity<String> response = update(basicAuthTemplate);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo(String.format("/issues/%d", ISSUE2.getId()));
    }

    @Test
    public void delete_no_login() {
        ResponseEntity<String> response = delete(template);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    private ResponseEntity<String> delete(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                        .delete()
                        .build();

        return template.postForEntity(String.format("/issues/%d", ISSUE3.getId()), request, String.class);
    }

    @Test
    public void delete_not_owner() {
        ResponseEntity<String> response = delete(basicAuthTemplate(JAVAJIGI));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo(String.format("/issues/%d", ISSUE3.getId()));
    }

    @Test
    public void delete() {
        ResponseEntity<String> response = delete(basicAuthTemplate(SANJIGI));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }


    @Test
    public void setMilestone_no_login() {
        ResponseEntity<String> response = template
                .getForEntity(String.format("/issues/%d/milestones/%d", 1, 1), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void setMilestone() {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/issues/%d/milestones/%d", ISSUE1.getId(), MILESTONE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo(String.format("/issues/%d", ISSUE1.getId()));
    }

    @Test
    public void close_no_login() {
        ResponseEntity<String> response = template
                .getForEntity(String.format("/issues/%d/closed", ISSUE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void close_already_closed() {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/issues/%d/closed", ISSUE4.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo(String.format("/issues/%d", ISSUE4.getId()));
    }

    @Test
    public void close() {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/issues/%d/closed", ISSUE1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}