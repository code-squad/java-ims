package codesquad.web;

import codesquad.domain.issue.answer.Answer;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiAttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiAttachmentControllerTest.class);

    @Test
    public void upload() {
        ResponseEntity<Answer> result = createAttachment();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void download() throws Exception {
        createAttachment();

        ResponseEntity<String> result = basicAuthTemplate().getForEntity("/api/issues/1/attachments/1", String.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", result.getBody());
    }

    public ResponseEntity<Answer> createAttachment() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        return basicAuthTemplate().postForEntity("/api/issues/1/attachments", request, Answer.class);
    }
}
