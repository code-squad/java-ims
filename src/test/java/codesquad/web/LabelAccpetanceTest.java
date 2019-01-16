package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
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

import static codesquad.domain.LabelTest.*;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class LabelAccpetanceTest  extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private LabelRepository labelRepository;

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/labels/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/labels/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("name", LABEL1.getName())
                        .build();

        ResponseEntity<String> response = template.postForEntity("/labels", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void create_already_exist_name() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("name", LABEL1.getName())
                        .build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("name", UPDATEDLABEL2.getName())
                        .build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/labels", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (Label label : LABELS) {
            softly.assertThat(response.getBody().contains(label.getName())).isTrue();
        }
    }

    @Test
    public void updateForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity(String.format("/labels/%d/form", LABEL1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void updateForm_not_owner() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate(SANJIGI).getForEntity(String.format("/labels/%d/form", LABEL1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void updateForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/labels/%d/form", LABEL1.getId()), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug(response.getBody());
        softly.assertThat(response.getBody().contains(LABEL1.getName())).isTrue();
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = update(template);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    private ResponseEntity<String> update(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("name", UPDATEDLABEL2.getName())
                .addParameter("writer", JAVAJIGI.getId())
                .build();

        return template.postForEntity(String.format("/labels/%d", LABEL2.getId()), request, String.class);
    }

    @Test
    public void update_not_owner() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate(SANJIGI));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void update_label_have_other_user_issue() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("name", UPDATEDLABEL6.getName())
                .addParameter("writer", SANJIGI.getId())
                .build();

        ResponseEntity<String> response = basicAuthTemplate(SANJIGI).postForEntity(String.format("/labels/%d", LABEL6.getId()), request, String.class);


        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void update_label_have_myself_issues() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("name", UPDATEDLABEL5.getName())
                .addParameter("writer", SANJIGI.getId())
                .build();

        ResponseEntity<String> response = basicAuthTemplate(SANJIGI).postForEntity(String.format("/labels/%d", LABEL5.getId()), request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void update_empty_issues() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void delete_no_login() throws Exception {
        ResponseEntity<String> response = delete(template);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    private ResponseEntity<String> delete(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        return template.postForEntity(String.format("/labels/%d", LABEL3.getId()), request, String.class);
    }

    @Test
    public void delete_not_owner() throws Exception {
        ResponseEntity<String> response = delete(basicAuthTemplate(SANJIGI));

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void delete_label_have_myself_issues() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> response = basicAuthTemplate(SANJIGI).postForEntity(String.format("/labels/%d", LABEL6.getId()), request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void delete_empty_issues() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> response = basicAuthTemplate(SANJIGI).postForEntity(String.format("/labels/%d", LABEL5.getId()), request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }
}
