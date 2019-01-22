package codesquad.web;

import codesquad.domain.Issue;
import codesquad.repository.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    private HtmlFormDataBuilder makeIssue() {
        return HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다.")
                .addParameter("comment", "내용입니다.");
    }

    @Test
    public void createForm_success() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssue().build();
        log.debug("request : {}", request);

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        log.debug("response : {}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void updateForm_success() {
        ResponseEntity<String> reponse = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(reponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateForm_no_login() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void updateForm_by_other_user() {
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("sanjigi")).getForEntity(String.format("/issues/%d/form", 1), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_success() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssue().put().build();

        log.debug("request:{}", request);
        ResponseEntity<Issue> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", 1), request, Issue.class);
        log.debug("response:{}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void update_no_login() {
        ResponseEntity<String> response =
                template.postForEntity(String.format("/issues/%d", 1), HtmlFormDataBuilder.urlEncodedForm().put().build(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update_by_other_user() {
        ResponseEntity<String> response =
                basicAuthTemplate(findByUserId("sanjigi")).postForEntity(String.format("/issues/%d", 1), HtmlFormDataBuilder.urlEncodedForm().put().build(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_success() {
        ResponseEntity<String> response =
                basicAuthTemplate.postForEntity(String.format("/issues/%d", 1), HtmlFormDataBuilder.urlEncodedForm().delete().build(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void delete_no_login() {
        ResponseEntity<String> response =
                template.postForEntity(String.format("/issues/%d", 1), HtmlFormDataBuilder.urlEncodedForm().delete().build(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_by_other_user() {
        ResponseEntity<String> response =
                basicAuthTemplate(findByUserId("sanjigi")).postForEntity(String.format("/issues/%d", 1), HtmlFormDataBuilder.urlEncodedForm().delete().build(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
