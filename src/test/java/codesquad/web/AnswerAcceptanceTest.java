package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(AnswerAcceptanceTest.class);

    @Test
    public void addAnswer() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("contents", "test contents").build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issue/1/answers", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue/1/answers", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
