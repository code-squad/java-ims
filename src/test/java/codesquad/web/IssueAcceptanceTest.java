package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(IssueAcceptanceTest.class);
    private IssueDto testIssue;

    @Autowired
    IssueRepository issueRepository;

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test code subject")
                .addParameter("comment", "test code comment").build();
        log.info("request : {}", request.toString());
        log.info("request body : {}", request.getBody());
        ResponseEntity<String> response = template.postForEntity("/issue", request, String.class);
        log.info("response : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issue"));
    }

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void show() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue/1", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void update() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method","put")
                .addParameter("subject", "updated subject")
                .addParameter("comment", "updated comment").build();
        ResponseEntity<String> response = template.postForEntity("/issue/1", request, String.class);
        log.info("status code : {}", response.getStatusCode());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issue"));
    }

    @Test
    public void delete() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
        ResponseEntity<String> response = template.postForEntity("/issue/2", request, String.class);
        assertThat(response.getHeaders().getLocation().getPath(), is("/issue"));
    }
}
