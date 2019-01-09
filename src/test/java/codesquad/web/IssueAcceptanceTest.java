package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.UserTest.RED;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("subject", "subject1")
                        .addParameter("comment", "comment1")
                        .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/");
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject1")
                .addParameter("comment", "comment1")
                .build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void show() {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject_showTest")
                .addParameter("comment", "comment_showTest")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        log.debug("response : {}", response.getBody());

        /*
        Issue issue = issueRepository.findBySubject("subject_showTest").orElse(null);

        ResponseEntity<String> responseEntity = template.getForEntity(String.format("/issues/%d", issue.getId()), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getBody()).contains(issue.getIssueBody().getComment());
        log.debug("body : {}", responseEntity.getBody());
        */
    }

    @Test
    public void updateForm_login() {
        ResponseEntity<String> response = basicAuthTemplate(RED).getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateForm_login_otherUser() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateForm_no_login() {
        ResponseEntity<String> response = template().getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update_login() {
        ResponseEntity<String> response = update(basicAuthTemplate(RED));
         softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void update_login_otherUser() {
        ResponseEntity<String> response = update(basicAuthTemplate());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void update_no_login() {
        ResponseEntity<String> response = update(template);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> update(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .urlEncodedForm()
                .put()
                .addParameter("subject", "updateSubject")
                .addParameter("comment", "updateComment")
                .build();

        return template.postForEntity(String.format("/issues/%d", 1), request, String.class);
    }

    @Test
    public void delete_login(){
        ResponseEntity<String> response = delete(basicAuthTemplate(RED));
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void delete_no_login(){
        ResponseEntity<String> response = delete(template);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_login_othwerUser() {
        ResponseEntity<String>  response = delete(basicAuthTemplate());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<String> delete(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .urlEncodedForm()
                .delete()
                .build();

        return template.postForEntity(String.format("/issues/%d", 1), request, String.class);
    }
}
