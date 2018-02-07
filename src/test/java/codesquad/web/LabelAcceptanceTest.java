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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LabelAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(LabelAcceptanceTest.class);

    @Test
    public void createForm_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template().getForEntity("/labels/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_login() {
        String subject = "label test";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/labels", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/labels/"));
    }

    @Test
    public void create_no_login() {
        String subject = "들어가면 안되";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .build();

        ResponseEntity<String> response = template().postForEntity("/labels", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_form_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels/1/form", String.class);

        logger.debug("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("1번 라베루"));
    }

    @Test
    public void update_form_other() {
        ResponseEntity<String> response = basicAuthTemplate(findByUserId("boobby")).getForEntity("/labels/1/form", String.class);

        logger.debug("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("작성자만 수정할 수 있습니다."));
    }

//    @Test
//    public void show_list() {
//        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
//
//        logger.debug("body : {}", response.getBody());
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//        assertTrue(response.getBody().contains("Milestone 1"));
//    }
}