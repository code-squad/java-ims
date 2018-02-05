package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueAcceptanceTest extends AcceptanceTest{
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void create() {
        String subject = "제모오오오옥";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "코메에에에에에에엔트")
                .build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);

        logger.debug("body : {}", response.getBody());
        assertTrue(response.getBody().contains("test issue1"));
        assertTrue(response.getBody().contains("테스트 1번 이슈입니다."));
    }
}