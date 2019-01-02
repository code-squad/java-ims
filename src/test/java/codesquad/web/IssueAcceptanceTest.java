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

import static codesquad.domain.IssueTest.ISSUES;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "testSubject")
                .addParameter("comment", "testComment")
                .build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findBySubject("testSubject")).isNotNull();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void show_not_exist_issue() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 99999999), String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void show() {
        for (Issue issue : ISSUES) {
            ResponseEntity<String> response = template().getForEntity(String.format("/issues/%d", issue.getId()), String.class);

            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(response.getBody()).contains(issueRepository.findById(issue.getId()).get().getSubject());
            softly.assertThat(response.getBody()).contains(issueRepository.findById(issue.getId()).get().getComment());
            log.debug("body : {}", response.getBody());
        }
    }
}
