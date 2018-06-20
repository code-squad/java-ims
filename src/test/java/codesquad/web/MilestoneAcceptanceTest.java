package codesquad.web;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final String CREATE_URL = "/milestones";

    @Test
    public void list() {
        ResponseEntity<String> response = getResource("/milestones", template());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_Logged_In() {
        ResponseEntity<String> response = getResource("/milestones/form", basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_NOT_Logged_In() {
        ResponseEntity<String> response = getResource("/milestones/form", template());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private ResponseEntity<String> createMilestone(TestRestTemplate template, String subject) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("startDate", "2018-06-19T12:00")
                .addParameter("dueDate", "2018-07-20T12:00")
                .addParameter("subject", subject)
                .build();
        return template.postForEntity(CREATE_URL, request, String.class);
    }

    @Test
    public void create_Logged_In() {
        ResponseEntity<String> response = createMilestone(basicAuthTemplate(), "subject1");
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        ResponseEntity<String> list = getResource("/milestones", template());
        assertThat(list.getBody().contains("subject1"), is(true));
    }

    @Test
    public void create_NOT_Logged_In() {
        ResponseEntity<String> response = createMilestone(template(), "subject2");
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

        ResponseEntity<String> list = getResource("/milestones", template());
        assertThat(list.getBody().contains("subject2"), is(false));
    }


}