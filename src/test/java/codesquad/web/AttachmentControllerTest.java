package codesquad.web;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Test
    public void download() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(HttpStatus.FOUND));

        String path = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> result = template.getForEntity(path + "/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> formRequest = makeIssueFormData();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", formRequest, String.class);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(HttpStatus.FOUND));

        String path = responseEntity.getHeaders().getLocation().getPath();

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = basicAuthTemplate().postForEntity(path + "/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertThat(result.getHeaders().getLocation().getPath(), is(path));
    }

    @Test
    public void upload_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> formRequest = makeIssueFormData();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", formRequest, String.class);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(HttpStatus.FOUND));

        String path = responseEntity.getHeaders().getLocation().getPath();

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template.postForEntity(path + "/attachments", request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }
}