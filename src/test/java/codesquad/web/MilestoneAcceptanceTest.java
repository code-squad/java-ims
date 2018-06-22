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
    private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void form() {
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/milestones/form");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_fail_unAuthentication() {
        ResponseEntity<String> response = requestGet(template(), "/milestones/form");
        assertTrue(response.getBody().contains("Login Member"));
    }

    static HttpEntity<MultiValueMap<String, Object>> requestCreateMilestone() {
        HtmlFormDataBuilder builder = HtmlFormDataBuilder.urlEncodedForm();
        builder.addParameter("subject", "test subject");
        builder.addParameter("startDate", "2018-06-22T10:11:30");
        builder.addParameter("endDate", "2015-06-29T14:15:30");

        return builder.build();
    }

    @Test
    public void create() {
        ResponseEntity<String> response = requestPost(basicAuthTemplate(), "/milestones", requestCreateMilestone());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(getPath(response).startsWith("/milestones"));
    }

    @Test
    public void create_fail_unAuthentication() {
        ResponseEntity<String> response = requestPost(template(), "/milestones", requestCreateMilestone());
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(getPath(response), is("/users/loginForm"));
    }

    @Test
    public void show() {
        String path = getPath(requestPost(template(), "/milestones", requestCreateMilestone()));
        ResponseEntity<String> response = requestGet(basicAuthTemplate(), "/milestones");
        assertTrue(response.getBody().contains("test subject"));
    }

}