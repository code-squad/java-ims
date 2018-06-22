package codesquad.web;

import codesquad.domain.MilestoneRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);
    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Test
    public void list() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void form_no_login() {
        ResponseEntity<String> response = template().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test")
                .addParameter("startDate", "2017-06-01T08:30")
                .addParameter("endDate", "2017-06-01T08:31").build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("Body : {}", response.getHeaders());
    }

    @Test
    public void create_다른_사용자() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test2")
                .addParameter("startDate", "2017-06-01T08:30")
                .addParameter("endDate", "2017-06-01T08:31").build();

        ResponseEntity<String> response = template().postForEntity("/milestones", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}
