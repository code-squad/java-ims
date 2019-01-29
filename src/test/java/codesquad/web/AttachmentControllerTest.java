package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger logger = getLogger(AttachmentControllerTest.class);

    @Test
    public void download() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
        softly.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();

        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);
        softly.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }
}
