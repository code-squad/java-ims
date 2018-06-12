package codesquad.web;

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

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest{

    private final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        String subject = "문제가 생겼습니다.";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "사실 없습니다.").build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findBySubject(subject));
    }

    @Test
    public void show() {
        String subject = "문제가 생겼습니다.";
        String comment = "사실 없습니다.";
        String location = createIssueLocation(subject, comment);
        ResponseEntity<String> response = template.getForEntity(location, String.class);
        assertTrue(response.getBody().contains(subject));
        assertTrue(response.getBody().contains(comment));
    }

}
