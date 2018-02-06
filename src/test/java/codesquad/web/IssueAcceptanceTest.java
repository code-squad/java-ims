package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import org.junit.Before;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    public ResponseEntity<String> createIssue() {
        return createIssue("test title");
    }

    public ResponseEntity<String> createIssue(String title) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("title", title)
                .addParameter("comment", "test comment")
                .build();

        return basicAuthTemplate(findDefaultUser()).postForEntity("/issues", request, String.class);
    }

    @Before
    public void cleanDb() {
        issueRepository.deleteAll();
    }

    @Test
    public void createTest() throws Exception {
        ResponseEntity<String> response = createIssue();

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findAll());
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void showTest() throws Exception {
        createIssue();
        ResponseEntity<String> response = template.getForEntity("/issues/2", String.class);

        assertThat(response.getBody().contains("test title"), is(true));
        assertThat(response.getBody().contains("test comment"), is(true));
    }

    @Test
    public void listShowTest() throws Exception {
        createIssue();
        createIssue("title2");
        ResponseEntity<String> response = template.getForEntity("/", String.class);

        assertThat(response.getBody().contains("test title"), is(true) );
        assertThat(response.getBody().contains("title2"), is(true) );
    }

    @Test
    public void updateTest_success() {
        createIssue();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("title", "new title")
                .addParameter("comment", "new comment")
                .build();

        ResponseEntity<String> response = basicAuthTemplate(findDefaultUser())
                .postForEntity("/issues/2", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void updateTest_fail() {
        createIssue();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("title", "new title")
                .addParameter("comment", "new comment")
                .build();

        User sehwan = new User("sehwan", "test", "test");
        ResponseEntity<String> response = basicAuthTemplate(sehwan)
                .postForEntity("/issues/2", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void deleteTest_success() {
        createIssue();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete")
                .build();

        ResponseEntity<String> response = basicAuthTemplate(findDefaultUser())
                .postForEntity("/issues/3", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void deleteTest_fail() {
        createIssue();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete")
                .build();

        User sehwan = new User("sehwan", "test", "test");
        ResponseEntity<String> response = basicAuthTemplate(sehwan)
                .postForEntity("/issues/1", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}
