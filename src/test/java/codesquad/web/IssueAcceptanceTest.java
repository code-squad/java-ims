package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    IssueRepository issueRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다")
                .addParameter("comment", "내용입니다.").build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findById(1L));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
    }

    @Test
    public void showList() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("it's issue subject found by {}", issueRepository.findById(1L).map(Issue::getSubject).get());
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getSubject).get()), is(true));
    }

    @Test
    public void showOne() {
        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getComment).get()), is(true));
        assertThat(response.getBody().contains(issueRepository.findById(1L).map(Issue::getSubject).get()), is(true));
    }
}
