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

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiAttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiAttachmentControllerTest.class);

    @Test
    public void upload() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }
}
