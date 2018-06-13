package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private HttpEntity<MultiValueMap<String, Object>> create(String title, String contents) {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("title", title);
        builder.addParameter("contents", contents);
        return builder.build();
    }

    @Test
    public void create() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, create("test title", "test contents"));
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("redirect uri : {}", response.getHeaders().getLocation().getPath());
    }

    @Test
    public void create_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), CREATE_PATH, create("test title", "test contents"));
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void create_fail_invalid_params() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, create("t", "t"));
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), CREATE_PATH, create("test title", "test contents"));
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
    }

    @Test
    public void edit_fail_not_found() {
    }

    @Test
    public void edit_fail_unAuthentication() {
    }

    @Test
    public void edit_fail_unAuthorized() {
    }

    @Test
    public void update() {
    }

    @Test
    public void update_fail_not_found() {
    }

    @Test
    public void update_fail_unAuthentication() {
    }

    @Test
    public void update_fail_unAuthorized() {
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
