package codesquad.web;

import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LogManager.getLogger(IssueAcceptanceTest.class);
    private static final String ISSUE_URL = "/issue";

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issue/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/login");
    }


    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = getStringResponseEntity(HtmlFormDataBuilder.urlEncodedForm(), "제목입니다.", "내용입니다.", basicAuthTemplate, ISSUE_URL);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findById(1L)).isNotEmpty();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    private ResponseEntity<String> getStringResponseEntity(HtmlFormDataBuilder htmlFormDataBuilder, String subject, String comment, TestRestTemplate basicAuthTemplate, String url) {
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder
                .addParameter("subject", subject)
                .addParameter("comment", comment).build();

        return basicAuthTemplate.postForEntity(url, request, String.class);
    }

    @Test
    public void create_not_login() throws Exception {
        ResponseEntity<String> response = getStringResponseEntity(HtmlFormDataBuilder.urlEncodedForm(), "제목입니다.", "내용입니다.", template, ISSUE_URL);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/login");
    }

    @Test
    public void update_form() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issue/1/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void update_form_not_login() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issue/1/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void update() {
        ResponseEntity<String> response = getStringResponseEntity(HtmlFormDataBuilder.urlEncodedForm()
                .put(), "나는 바뀐 제목입니다.", "나는 바뀐 내용입니다.", basicAuthTemplate, ISSUE_URL + "/1");
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        log.debug(response.getBody());
    }

    @Test
    public void update_not_login() {
        ResponseEntity<String> response = getStringResponseEntity(HtmlFormDataBuilder.urlEncodedForm()
                .put(), "나는 바뀐 제목입니다.", "나는 바뀐 내용입니다.", template, ISSUE_URL + "/1");
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/login");
    }

    @Test
    public void update_not_others() {
        ResponseEntity<String> response = getStringResponseEntity(HtmlFormDataBuilder.urlEncodedForm()
                .put(), "나는 바뀐 제목입니다.", "나는 바뀐 내용입니다.", basicAuthTemplate(findByUserId("jar100")), ISSUE_URL + "/1");
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = basicAuthTemplate.postForEntity(ISSUE_URL + "/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void delete_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = template.postForEntity(ISSUE_URL + "/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/login");    }

    @Test
    public void delete_no_others() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("jar100")).postForEntity(ISSUE_URL + "/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void setMilestone() {
        ResponseEntity<String> response = template.getForEntity("/issue/1/setMilestone/1",String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findById(1L).get().getMilestone().getSubject()).isEqualTo("취업");
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/issue/1");
    }

    @Test
    public void setLabel() {
        ResponseEntity<String> response = template.getForEntity("/issue/1/setLabel/1",String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        //softly.assertThat(issueRepository.findById(1L).get().getLabel().getSubject()).isEqualTo("라벨");
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/issue/1");
    }


    @Test
    public void setAssignee() {
        ResponseEntity<String> response = template.getForEntity("/issue/1/setAssignee/3",String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        User user = (User) issueRepository.findById(1L).get().getAssignee();
        softly.assertThat(user.getName()).isEqualTo("Peter");
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/issue/1");
    }
}

