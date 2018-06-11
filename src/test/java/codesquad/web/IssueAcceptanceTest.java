package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void list() {
        ResponseEntity<String> response = template().getForEntity("/", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_NOT_Logged_In() {
        ResponseEntity<String> response = template().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_Logged_In() {

    }

    @Test
    public void create_NOT_Logged_In() {
        Issue issue = new Issue("title", "content");
        ResponseEntity<String> response = template().postForEntity("/issues", issue, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));

        ResponseEntity<String> resource = template().getForEntity("/", String.class);
        assertThat(resource.getStatusCode(), is(HttpStatus.OK));
        assertThat(resource.getBody().contains(issue.getContent()), is(true));
    }

    @Test
    public void create_Logged_In() {

    }
}