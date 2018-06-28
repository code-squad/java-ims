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
import static org.junit.Assert.*;

public class AttachmentAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentAcceptanceTest.class);
    private static final String XML_SAMPLE_LINE = "SAMPLE XML CONTENT";

    @Test
    public void download() {
        ResponseEntity<String> result = basicAuthTemplate().getForEntity("/issues/1/attachments/1", String.class);
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertTrue(result.getBody().contains(XML_SAMPLE_LINE));
    }

    @Test
    public void download_NOT_Logged_In() {
        ResponseEntity<String> result = template().getForEntity("/issues/1/attachments/1", String.class);
        assertThat(result.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void download_EntityNotFound() {
        ResponseEntity<String> result = basicAuthTemplate().getForEntity("/issues/1/attachments/2", String.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void upload() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/1/attachments", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void upload_NOT_Logged_In() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> response = template().postForEntity("/issues/1/attachments", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}