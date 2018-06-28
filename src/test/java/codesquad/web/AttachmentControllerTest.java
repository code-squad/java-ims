package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.HtmlFormDataBuilder;
import support.test.AcceptanceTest;

import static org.junit.Assert.assertEquals;

public class AttachmentControllerTest extends AcceptanceTest{

    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Test
    public void download_success() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = makeUploadRequest();
        String path = loginCreateResource("/issues/1/attachments", request);
        log.info("resource save path : {}", path);
        ResponseEntity<String> result = basicAuthTemplate().getForEntity("/issues/1/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.info("body : {}", result.getBody());
    }

    @Test
    public void download_fail_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = makeUploadRequest();
        String path = loginCreateResource("/issues/1/attachments", request);
        log.info("resource save path : {}", path);
        template.getForEntity("/users/logout", String.class);
        ResponseEntity<String> result = template.getForEntity("/issues/1/attachments/1", String.class);
        log.info("body : {}", result.getBody());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void upload_success() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = makeUploadRequest();
        ResponseEntity<String> result = basicAuthTemplate().postForEntity("/issues/1/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void upload_fail_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = makeUploadRequest();
        ResponseEntity<String> result = template().postForEntity("/issues/1/attachments", request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    private HttpEntity<MultiValueMap<String, Object>> makeUploadRequest() {
        return HtmlFormDataBuilder
                    .multipartFormData()
                    .addParameter("file", new ClassPathResource("logback.xml"))
                    .build();
    }


}