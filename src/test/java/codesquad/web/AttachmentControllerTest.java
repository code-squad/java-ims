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

import static org.junit.Assert.assertEquals;

//서버에 파일을 첨부할 때 사용자가 같은 이름의 파일을 첨부할 때 어떻게 처리할 것인지 고려해야 한다.
//파일 이름에 한글 이름이 포함되는 경우 깨지는 경우가 종종 발생한다. 한글 이름의 파일 처리도 고려해야 한다.
//----------------------------------------------------------------------------------------------------------

//로컬에 있는 특정 파일을 읽어 서버로 전송

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

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
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }
}