package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    private static final String CREATE_PATH = "/issues";

    private HttpEntity<MultiValueMap<String, Object>> getRequest(String title, String contents) {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("title", title);
        builder.addParameter("contents", contents);
        return builder.build();
    }

    @Test
    public void create() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents"));
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("redirect uri : {}", response.getHeaders().getLocation().getPath());
    }

    @Test
    public void create_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), CREATE_PATH, getRequest("test title", "test contents"));
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void create_fail_invalid_params() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("t", "t"));
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents"));
        String path = getPath(response);

        response = requestGet(path);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void show_fail_not_found() {
        ResponseEntity<String> response = requestGet("/issues/1000");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void edit() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents"))) + "/edit";
        log.debug("edit path : {}", editPath);
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), editPath);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void edit_fail_not_found() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/issues/100/edit");
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void edit_fail_unAuthentication() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents"))) + "/edit";
        ResponseEntity<String> response = requestGet(template(), editPath);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void edit_fail_unAuthorized() {
        String editPath = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents"))) + "/edit";
        ResponseEntity<String> response = requestGet(basicAuthTemplate(findByUserId("sanjigi")), editPath);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }


    private ResponseEntity<String> update(TestRestTemplate template) {
        String path = getPath(requestPost(basicAuthTemplate(), CREATE_PATH, getRequest("test title", "test contents")));

        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("_method", "put");
        builder.addParameter("title", "modify title");
        builder.addParameter("contents", "modify content");
        HttpEntity<MultiValueMap<String, Object>> request = builder.build();
        return template.postForEntity(path, request, String.class);
    }

    @Test
    public void update() {
        ResponseEntity<String> response = update(basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void update_fail_unAuthentication() {
        ResponseEntity<String> response = update(template());
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void update_fail_unAuthorized() {
        ResponseEntity<String> response = update(basicAuthTemplate(findByUserId("sanjigi")));
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete_fail_not_found() {
    }

    @Test
    public void delete_fail_unAuthentication() {
    }

    @Test
    public void delete_fail_unAuthorized() {
    }
}
