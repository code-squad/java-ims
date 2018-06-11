package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void list() {
        ResponseEntity<String> response = getResource("/");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form() {
        ResponseEntity<String> response = getResource("/issues/form");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void create() {
        ResponseEntity<String> response = createNewIssue();
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));

        ResponseEntity<String> resource = getResource("/");
        assertThat(resource.getStatusCode(), is(HttpStatus.OK));
        assertThat(resource.getBody().contains("test title"), is(true));
    }

    @Test
    public void show() {
        createNewIssue();
        ResponseEntity<String> response = getResource("/issues/1");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("test title"), is(true));
        assertThat(response.getBody().contains("test content"), is(true));
    }

    private ResponseEntity<String> getResource(String url) {
        return template().getForEntity(url, String.class);
    }

    private ResponseEntity<String> createNewIssue() {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("title", "test title");
        builder.addParameter("content", "test content");
        HttpEntity<MultiValueMap<String, Object>> request = builder.build();
        return template().postForEntity("/issues", request, String.class);
    }
}