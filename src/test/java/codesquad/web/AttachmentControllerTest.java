package codesquad.web;

import static org.junit.Assert.*;

import codesquad.domain.Issue;
import codesquad.domain.IssueTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    private Issue issue;

    @Before
    public void setUp() throws Exception {
        this.issue = IssueTest.FIRST_ISSUE;
    }

    @Test
    public void download() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
        softly.assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        log.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/attachments/1", request, String.class);
        softly.assertThat(HttpStatus.FOUND).isEqualTo(result.getStatusCode());
    }
}