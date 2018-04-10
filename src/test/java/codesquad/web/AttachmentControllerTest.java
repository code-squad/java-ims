package codesquad.web;

import static org.junit.Assert.*;

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


    public ResponseEntity<String> createTestFile(String fileName){
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource(fileName))
                .build();
        return template.postForEntity(String.format("/attachments/issues/%d",1), request, String.class);
    }
    @Test
    public void download() throws Exception {
        createTestIssue("download test issue", "download test contents");
        createTestFile("logback-access.xml");
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.info("body : {}", result.getBody());
        log.info("header : {}", result.getHeaders());
    }

    @Test
    public void upload() throws Exception {
        createTestIssue("upload test issue", "upload test contents");
        ResponseEntity<String> result = createTestFile("logback-access.xml");
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        createTestFile("logback-access.xml");
    }
}
