package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HomeAcceptanceTest extends AcceptanceTest {

    @Test
    public void show() throws Exception {
        String subject = "문제가 생겼습니다.";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "사실 없습니다.").build();
        basicAuthTemplate().postForEntity("/issues", request, String.class); // create
        ResponseEntity<String> response = template.getForEntity("/", String.class); // index.html

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains(subject));
    }

}
