package codesquad.web;

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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AttachmentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentAcceptanceTest.class);
    private static final String UPLOAD_PATH = "/issues/1/attachment";

    private HttpEntity<MultiValueMap<String, Object>> getFileUploadRequest() {
        return HtmlFormDataBuilder.multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
    }

    @Test
    public void upload() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), UPLOAD_PATH, getFileUploadRequest());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void upload_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), UPLOAD_PATH, getFileUploadRequest());
        assertEquals("/users/loginForm", getPath(response));
    }
}