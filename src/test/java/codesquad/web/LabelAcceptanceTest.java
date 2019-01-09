package codesquad.web;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.IssueTest.originalIssue;
import static codesquad.domain.LabelTest.originalLabel;

public class LabelAcceptanceTest extends BasicAuthAcceptanceTest {

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response =
                template.getForEntity("/labels/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createForm_login() {
        ResponseEntity<String> response =
                basicAuthTemplate.getForEntity("/labels/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("name", "name")
                .addParameter("explanation", "explanation")
                .build();

        ResponseEntity<String> response =
                basicAuthTemplate.postForEntity("/labels", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels/list");
    }

    @Test
    public void createUpdateForm_no_login() {
        ResponseEntity<String> response =
                template.getForEntity(String.format("/labels/%d/form", originalIssue.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createUpdateForm_login() {
        ResponseEntity<String> response =
                basicAuthTemplate.getForEntity(String.format("/labels/%d/form", originalIssue.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("name", "updatedName")
                .addParameter("explanation", "updatedExplanation")
                .build();
        return template.postForEntity(String.format("/labels/%d", originalLabel.getId()), request, String.class);
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = update(template);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_login() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath())
                .isEqualTo(String.format("/labels/%d", originalLabel.getId()));
    }

    private ResponseEntity<String> delete(TestRestTemplate template) throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        return template.postForEntity(String.format("/labels/%d", originalLabel.getId()), request, String.class);
    }

    @Test
    public void delete_no_login() throws Exception {
        ResponseEntity<String> response = delete(template);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete_login() throws Exception {
        ResponseEntity<String> response = delete(basicAuthTemplate);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath())
                .isEqualTo("/labels/list");
    }
}
