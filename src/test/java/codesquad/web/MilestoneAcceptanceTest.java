package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.MilestoneTest.DATE;
import static codesquad.domain.MilestoneTest.MILESTONE1;
import static codesquad.domain.MilestoneTest.MILESTONES;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/milestones/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create_no_login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("subject", MILESTONE1.getSubject())
                        .addParameter("startDate", MILESTONE1.getStartDate().toString())
                        .addParameter("endDate", MILESTONE1.getEndDate().toString())
                        .build();

        ResponseEntity<String> response = template.postForEntity("/milestones", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/users/login");
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request =
                HtmlFormDataBuilder.urlEncodedForm()
                        .addParameter("subject", "testMilestone1")
                        .addParameter("startDate", DATE)
                        .addParameter("endDate", DATE)
                        .build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/milestones");
    }

    @Test
    public void list() {
        ResponseEntity<String> responseEntity = template.getForEntity("/milestones", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        for (Milestone milestone : MILESTONES) {
            softly.assertThat(responseEntity.getBody().contains(milestone.getSubject())).isTrue();
        }
    }
}
