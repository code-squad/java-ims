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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final String DEFAULT_DELETE_URL = "/issues/1";
    private static final String CREATE_URL = "/issues";
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    private ResponseEntity<String> getResource(String url, TestRestTemplate template) {
        return template.getForEntity(url, String.class);
    }

    private ResponseEntity<String> createNewIssue(TestRestTemplate template, String title, String content) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", title)
                .addParameter("content", content)
                .build();
        return template.postForEntity(CREATE_URL, request, String.class);
    }

    private ResponseEntity<String> updateIssue(TestRestTemplate template, String location, String content) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", "title")
                .addParameter("content", content)
                .addParameter("_method", "put")
                .build();
        return template.postForEntity(location, request, String.class);
    }

    @Test
    public void list() {
        ResponseEntity<String> response = getResource("/", template());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_Logged_In() {
        ResponseEntity<String> response = getResource("/issues/form", basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_NOT_Logged_In() {
        ResponseEntity<String> response = getResource("/issues/form", template());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_Logged_In() {
        ResponseEntity<String> response = createNewIssue(basicAuthTemplate(), "title1", "content1");
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        String location = response.getHeaders().getLocation().getPath();

        ResponseEntity<String> resource = getResource(location, template());
        assertThat(resource.getStatusCode(), is(HttpStatus.OK));
        assertThat(resource.getBody().contains("title1"), is(true));
        assertThat(resource.getBody().contains("content1"), is(true));
    }

    @Test
    public void create_NOT_Logged_In() {
        ResponseEntity<String> response = createNewIssue(template(), "title2", "content2");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        ResponseEntity<String> resource = getResource("/", template());
        assertThat(resource.getStatusCode(), is(HttpStatus.OK));
        assertThat(resource.getBody().contains("title2"), is(false));
    }

    @Test
    public void show() {
        ResponseEntity<String> createResponse = createNewIssue(basicAuthTemplate(), "title3", "content3");
        String location = createResponse.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = getResource(location, template());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("title3"), is(true));
        assertThat(response.getBody().contains("content3"), is(true));
    }

    @Test
    public void update_Logged_In() {
        ResponseEntity<String> createResponse = createNewIssue(basicAuthTemplate(), "title4", "content4");
        String location = createResponse.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = updateIssue(basicAuthTemplate(), location, "updated content1");
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        ResponseEntity<String> resource = getResource(location, template());
        assertThat(resource.getBody().contains("updated content1"), is(true));
    }

    @Test
    public void update_NOT_Logged_In() {
        ResponseEntity<String> createResponse = createNewIssue(basicAuthTemplate(), "title5", "content5");
        String location = createResponse.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = updateIssue(template(), location, "updated content2");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private ResponseEntity<String> deleteIssue(TestRestTemplate template) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete")
                .build();
        return template.postForEntity(DEFAULT_DELETE_URL, request, String.class);
    }

    @Test
    public void delete_Logged_In() {
        ResponseEntity<String> response = deleteIssue(basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void delete_NOT_Logged_In() {
        ResponseEntity<String> response = deleteIssue(template());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}