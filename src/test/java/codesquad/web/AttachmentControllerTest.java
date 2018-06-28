package codesquad.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import support.HtmlFormDataBuilder;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AttachmentControllerTest {

    @Autowired
    private TestRestTemplate template;

    public TestRestTemplate template() {
        return template;
    }

    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);
//
//    @Test
//    public void download() throws Exception {
//        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        log.debug("body : {}", result.getBody());
//    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/issues/1/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }
}