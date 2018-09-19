package codesquad.web;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class AttachmentAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentAcceptanceTest.class);

    @Test
    public void uploadAndDownload() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();

        ResponseEntity<String> response = template.postForEntity("/issues/1/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());

        response = template.getForEntity("/issues/1/attachments/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.TEXT_XML_VALUE, response.getHeaders().getContentType().toString());
    }
}
