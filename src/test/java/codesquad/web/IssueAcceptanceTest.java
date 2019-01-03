package codesquad.web;

import codesquad.domain.IssueRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.UserTest.SANJIGI;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LogManager.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    //todo 중복코드 리팩토링
    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issue/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        log.debug("body : {}", response.getBody());
    }


    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다.")
                .addParameter("comment", "내용입니다.").build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue", request, String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findById(1L)).isNotEmpty();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void create_not_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다.")
                .addParameter("comment", "내용입니다.").build();

        ResponseEntity<String> response = template.postForEntity("/issue", request, String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
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
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", "나는 바뀐 제목입니다.")
                .addParameter("comment", "나는 바뀐 내용입니다.")
                .build();
        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        log.debug(response.getBody());
        //Todo 바뀐내용을 확인해야한다.
    }

    @Test
    public void update_not_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", "나는 바뀐 제목입니다.")
                .addParameter("comment", "나는 바뀐 내용입니다.")
                .build();
        ResponseEntity<String> response = template.postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_not_others() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", "나는 바뀐 제목입니다.")
                .addParameter("comment", "나는 바뀐 내용입니다.")
                .build();
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("jar100")).postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void delete_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = template.postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_no_others() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("jar100")).postForEntity("/issue/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}

