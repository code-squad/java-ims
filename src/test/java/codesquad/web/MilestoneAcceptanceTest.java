package codesquad.web;

import codesquad.domain.MilestoneRepository;
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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create() {
        ResponseEntity<String> responseEntity = makeMilestone("test subject");

        String path = getResponseLocation(responseEntity);
        log.debug("path : {}", path);

        assertTrue(path.startsWith("/milestones"));
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("startDate", "2018-07-21T01:59")
                .addParameter("endDate", "2018-10-24T11:58")
                .build();

        ResponseEntity<String> responseEntity = template.postForEntity("/milestones", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void list() {
        makeMilestone("test subject1");
        makeMilestone("test subject2");
        makeMilestone("test subject3");

        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("test subject1"));
        assertTrue(response.getBody().contains("test subject2"));
        assertTrue(response.getBody().contains("test subject3"));
    }
}
