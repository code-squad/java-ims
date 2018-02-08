package codesquad.web;

import static org.junit.Assert.*;

import codesquad.service.AttachmentService;
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

import javax.annotation.Resource;

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Resource(name="attachmentService")
    private AttachmentService attachmentService;

    @Test
    public void download() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("import.sql"))
                .build();
        ResponseEntity<String> result = basicAuthTemplate().postForEntity("/attachments", request, String.class);
        log.debug("it's list!: {}", attachmentService.findAllAttachments());

        assertEquals(HttpStatus.FOUND, result.getStatusCode());

    }
}