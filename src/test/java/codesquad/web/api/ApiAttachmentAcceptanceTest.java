package codesquad.web.api;

import codesquad.domain.issue.File;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.junit.Assert.assertEquals;

public class ApiAttachmentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiAttachmentAcceptanceTest.class);

    @Test
    public void download() throws Exception {
        ResponseEntity<FileSystemResource> result = basicAuthTemplate().getForEntity("/api/issues/1/attachments/1", FileSystemResource.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("body!!!!!!!!!!!!!!!! : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<File> result = basicAuthTemplate().postForEntity("/api/issues/1/attachments", request, File.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
