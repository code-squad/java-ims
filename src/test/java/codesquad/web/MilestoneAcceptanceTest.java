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

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void createForm_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_login() {
        String subject = "milestone";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "2017-12-22")
                .addParameter("endDate", "2018-03-02")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/milestones/"));
    }

    @Test
    public void create_no_login() {
        String subject = "들어가면 안되";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "2017-12-22")
                .addParameter("endDate", "2018-03-02")
                .build();

        ResponseEntity<String> response = template().postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void show_list() {
        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);

        logger.debug("body : {}", response.getBody());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("Milestone 1"));
    }
}